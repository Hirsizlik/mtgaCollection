package hirsizlik.mtgacollection.mapper;

public class MappingException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private final boolean isImportant;

	public MappingException(final String message, final Object notMappableObject, final boolean isImportant) {
		super(message + System.lineSeparator() + notMappableObject.toString());
		this.isImportant = isImportant;
	}

	public boolean isImportant() {
		return isImportant;
	}

}
