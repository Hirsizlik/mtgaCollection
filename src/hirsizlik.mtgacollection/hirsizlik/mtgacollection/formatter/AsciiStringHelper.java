package hirsizlik.mtgacollection.formatter;

import java.util.Arrays;

/**
 * Helperclass to format a encoded string with Ansi escape sequenced.
 * @author Markus Schagerl
 * @see https://stackoverflow.com/questions/4842424/list-of-ansi-color-escape-sequences
 */
public class AsciiStringHelper {

	private enum Color {
		WHITE("\033[97m"), CYAN("\033[96m"), YELLOW("\033[93m"), RED("\033[91m"), DEFAULT("\033[0m"), BOLD("\033[1m"),
		ITALIC("\033[3m");

		private final String sequence;

		private Color(final String sequence) {
			this.sequence = sequence;
		}

		String replace(final String s) {
			return s.replace(String.format("@%s", this.name()), this.sequence);
		}
	}

	/**
	 * Returns a coded string with ascii escape sequences. Colors are
	 * WHITE, CYAN, YELLOW and RED, Styles are BOLD and ITALIC. DEFAULT returns the style back to normal.
	 * Use a '@' to encode a color.
	 * For example: <div><code>"@CYANI am in Cyan@BOLD and now also bold@DEFAULT and back to normal."</code></div>
	 * @param codedString the encoded string
	 * @return the string with ansi escape sequences
	 */
	public static String getAsciiString(final String codedString) {
		/*
		 * Combiner is only used if executed in parallel.
		 * It would have to combine to partially decoded strings to one.
		 * That would be complicated, so instead just use sequential() to be sure and use "null" as combiner.
		 *
		 */
		return Arrays.stream(Color.values()).sequential().reduce(codedString, (s, c) -> c.replace(s), (s1, s2) -> null);
	}
}
