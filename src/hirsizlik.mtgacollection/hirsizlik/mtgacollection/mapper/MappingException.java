package hirsizlik.mtgacollection.mapper;

/**
 * Exception for errors while Mapping.
 *
 * @author Markus Schagerl
 * @see MappingResult
 */
public class MappingException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private final boolean isImportant;

	/**
	 * Creates a new Instance.
	 *
	 * @param message the Exception-Message
	 * @param notMappableObject the original Object which could not be mapped. It's toString will be appended to the message.
	 * @param isImportant if this exception is important and should be handled, or be silently ignored if not.
	 */
	public MappingException(final String message, final Object notMappableObject, final boolean isImportant) {
		super(message + System.lineSeparator() + notMappableObject.toString());
		this.isImportant = isImportant;
	}

	public boolean isImportant() {
		return isImportant;
	}

}
