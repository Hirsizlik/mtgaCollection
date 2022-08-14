package hirsizlik.mtgacollection.mapper;

/**
 * Exception for errors while Mapping.
 *
 * @author Markus Schagerl
 * @see MappingResult
 */
public class MappingException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new Instance.
	 *
	 * @param message the Exception-Message
	 * @param notMappableObject the original Object which could not be mapped. It's toString will be appended to the message.
	 */
	public MappingException(final String message, final Object notMappableObject) {
		super(message + System.lineSeparator() + notMappableObject.toString());
	}

}
