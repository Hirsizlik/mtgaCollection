package hirsizlik.mtgacollection.mapper;

import java.util.function.Consumer;

/**
 * Represents the result of a mapping process.
 * @author Markus Schagerl
 *
 * @param <T> the result type (if mapping was ok)
 * @param <B> the input type
 */
public class MappingResult<B, T> {
	private final B base;
	private final T result;

	private final MappingException me;

	private MappingResult(final B base, final T result, final MappingException me) {
		this.base = base;
		this.result = result;
		this.me = me;
	}

	/**
	 * Creates a error with a message.
	 * The error is marked as important.
	 * @param <B> the base type
	 * @param <T> the result type, unused as this is a error
	 * @param notMappableObject the object that wasn't mapped
	 * @param message the error message
	 * @return a mappingResult with an error
	 */
	public static <B, T> MappingResult<B, T> createError(final B notMappableObject, final String message) {
		return createError(notMappableObject, message, true);
	}

	/**
	 * Creates a error with a message.
	 * @param <B> the base type
	 * @param <T> the result type, unused as this is a error
	 * @param notMappableObject the object that wasn't mapped
	 * @param message the error message
	 * @param isImportant true if the error should be considered "important", which should at least be logged.
	 * Non important errors can be ignored.
	 * @return a mappingResult with an error
	 */
	public static <B, T> MappingResult<B, T> createError(final B notMappableObject, final String message,
			final boolean isImportant) {
		return new MappingResult<>(notMappableObject, null, new MappingException(message, notMappableObject,
				isImportant));
	}

	/**
	 * Creates a successful result.
	 * @param <B> the base type
	 * @param <T> the result type
	 * @param base the base object
	 * @param result the mapping result
	 * @return a successful mapping
	 */
	public static <B, T> MappingResult<B, T> createOk(final B base, final T result) {
		return new MappingResult<>(base, result, null);
	}

	/**
	 * Executes the consumer if this depicts a successful mapping, otherwise runs the Runnable.
	 * @param consumerOk the consumer which will be run if ok
	 * @param notOk the runnable executed if not ok
	 */
	public void ifOkOrElse(final Consumer<T> consumerOk, final Runnable notOk) {
		if(me == null) {
			consumerOk.accept(result);
		}else {
			notOk.run();
		}
	}

	/**
	 * Simply returns true if this mapping is successful, otherwise execute the consumer with this object.
	 * @param elseDo the consumer executed if not successful.
	 * @return true if successful, false otherwise
	 */
	public boolean returnTrueIfOkOrElse(final Consumer<MappingResult<B, T>> elseDo) {
		if(me != null) {
			elseDo.accept(this);
			return false;
		}

		return true;
	}

	/**
	 * Returns the mapped type if successful, otherwise throw the MappingException.
	 * @return the mapped type
	 * @throws MappingException the exception if not successful
	 */
	public T get() throws MappingException{
		if(me != null) {
			throw me;
		}

		return result;
	}

	/**
	 * @return the base object used for mapping
	 */
	public B getBase(){
		return base;
	}

	/**
	 * @return the exception if not successful, null otherwise
	 */
	public MappingException getException() {
		return me;
	}
}
