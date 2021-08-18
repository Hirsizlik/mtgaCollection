package hirsizlik.mtgacollection.parser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Reads the logfile and extracts blocks (marked by '{' and '}') after strings.
 * @author Markus Schagerl
 *
 */
public class LogfileBlockReader implements AutoCloseable{
	private final FileChannel fc;
	private final ByteBuffer bb;

	private static final long NOT_FOUND = -1;
	private static final int BUFFER_SIZE = 1 << 12;

	/**
	 * Creates a new reader. Has to be closed after done business.
	 * @param pathToLogfile path to log
	 * @throws IOException error while opening the channel
	 */
	public LogfileBlockReader(final Path pathToLogfile) throws IOException {
		this.fc = FileChannel.open(pathToLogfile, StandardOpenOption.READ);
		this.bb = ByteBuffer.allocate(BUFFER_SIZE);
	}

	/**
	 * Returns blocks after the given lines
	 * @param searchLines the lines to be searched and the block following to be returned. Each value has to be unique.
	 * @return the map with the searchLine as key and the found block as value
	 * @throws IOException error while doing buffer stuff
	 * @throws IllegalArgumentException either one of the lines is to big or duplicate
	 */
	public Map<String, String> getLastBlockAfter(final String... searchLines) throws IOException {
		checkSeachLineSize(searchLines);
		checkSearchLineDuplicates(searchLines);

		long[] blockStartAt = new long[searchLines.length];

		Arrays.fill(blockStartAt, NOT_FOUND);
		fc.position(fc.size() - BUFFER_SIZE); // start from (fileEnd - BUFFER_SIZE)
		while(containsNotFound(blockStartAt)) {
			Set<Long> possibleStartsAt = new HashSet<>();
			long posBefore = fc.position();
			clearReadFlip();

			// check if a start is found
			while(bb.position() < bb.limit()) {
				byte b = bb.get();

				for (String searchLine : searchLines) {
					if (b == searchLine.getBytes()[0]) { // 1st char (or Byte)
						possibleStartsAt.add(posBefore + bb.position() - 1);// save possible start
					}
				}
			}

			// check if one was found
			if(!possibleStartsAt.isEmpty()) {
				setBlockStartsIfCorrect(possibleStartsAt, blockStartAt, searchLines);
			}

			if(posBefore == 0) // stop if at file start
				break;
			// set fileChannel back by BUFFER_SIZE or back to 0 if the resulting position would be negative
			fc.position(Math.max(posBefore - BUFFER_SIZE, 0));
		}

		return readBlocks(blockStartAt, searchLines);
	}

	/**
	 * Reads the blocks after their found start positions
	 * @param blockStartAt array of block start positions.
	 * @param searchLines the search lines
	 * @return Map with search line as key and block string as value
	 * @throws IOException error while reading
	 */
	private Map<String, String> readBlocks(final long[] blockStartAt, final String... searchLines) throws IOException {
		Map<String, String> blockMap = new HashMap<>();
		for(int i = 0; i < searchLines.length; i++) {
			if(blockStartAt[i] == NOT_FOUND) {
				String m = String.format("'%s' wasn't found!", searchLines[i]);
				throw new IllegalStateException(m);
			}
			blockMap.put(searchLines[i], readBlockFrom(blockStartAt[i]));
		}
		return blockMap;
	}

	/**
	 * Check if the one of the given search lines would bust the buffer
	 * @param searchLines the lines
	 * @throws IllegalArgumentException if a line would bust the buffer
	 */
	private void checkSeachLineSize(final String... searchLines) {
		for(String s : searchLines) {
			if(s.getBytes().length > BUFFER_SIZE) {
				throw new IllegalArgumentException(String.format("'%s' is too large (max: %d bytes)", s, BUFFER_SIZE));
			}
		}
	}

	/**
	 * Checks if the array contains duplicate values and throws if so.
	 * @param searchLines the lines
	 * @throws IllegalArgumentException a duplicate value was found
	 */
	private void checkSearchLineDuplicates(final String... searchLines) {
		long distinct = Arrays.stream(searchLines).distinct().count();
		if(searchLines.length != distinct) {
			throw new IllegalArgumentException("searchLines must be unique!");
		}
	}

	/**
	 * Reads the next JSON block starting from the provided position.
	 * @param position the fileChannel-position to start reading from
	 * @return the next JSON block after the position
	 * @throws IOException error while reading
	 */
	private String readBlockFrom(final long position) throws IOException {
		fc.position(position);
		StringBuilder sb = new StringBuilder(2048);

		int curlyCounter = 0;

		while(true) {
			int read = clearReadFlip();
			if(read == 0)
				throw new IllegalStateException("No Character read, no valid block?");
			while(bb.hasRemaining()) {
				char c = (char)bb.get();
				sb.append(c);
				if(c == '{')
					curlyCounter++;
				if(c == '}') {
					curlyCounter--;
					if(curlyCounter == 0)
						return sb.toString();
				}
			}
		}
	}

	/**
	 * Clears the ByteBuffer, reads from the FileChannel, flips the buffer and returns the
	 * amount of bytes read.
	 * @return bytes read
	 * @throws IOException error during reading
	 */
	private int clearReadFlip() throws IOException {
		bb.clear();
		int read = fc.read(bb);
		bb.flip();

		return read;
	}

	/**
	 * Sets a value in the array if a search if a search line matches.
	 * That value equals the position before the starting '{' after the search line.
	 * The latter found position is used if multiple are found.
	 * @param foundStartsAt set of positions where a search line may be found
	 * @param blockStartAt will be set if a search line was found
	 * @param searchLines the search pattern
	 * @throws IOException exception at FileChannel.position(..)
	 */
	private void setBlockStartsIfCorrect(final Set<Long> foundStartsAt, final long[] blockStartAt,
			final String... searchLines) throws IOException {
		for(Long startAt : foundStartsAt){
			fc.position(startAt);

			clearReadFlip();

			for(int j = 0; j < searchLines.length; j++) {

				if(startsWithAndAdvanceBufferIfSo(searchLines[j].getBytes())) {
					// find next '{' and save its position
					// assume that the Buffer is big enough so that it can be found
					skipToNextCurly();
					long blockStart = startAt + bb.position() - 1; // including '{'
					if(blockStartAt[j] < blockStart)
						blockStartAt[j] = blockStart;

					break;
				}
			}
		}
	}

	private void skipToNextCurly() {
		while(true) {
			if (bb.get() == (byte) '{') break;
		}
	}

	/**
	 * Checks if the ByteBuffer starts with the the given byte[].
	 * If so true will be returned, otherwise false.
	 * The buffer will be reset if false is returned.
	 * @param searchLineBA the line as byte[]
	 * @return true if the Buffer starts with the searchLine, otherwise false.
	 */
	private boolean startsWithAndAdvanceBufferIfSo(final byte[] searchLineBA) {
		bb.mark();
		for (byte value : searchLineBA) {
			byte b = bb.get();

			if (b != value) {
				bb.reset();
				return false;
			}
		}
		return true;
	}

	private boolean containsNotFound(final long[] array) {
		for(long l : array) {
			if(l == NOT_FOUND)
				return true;
		}

		return false;
	}

	@Override
	public void close() throws IOException {
		fc.close();
	}
}
