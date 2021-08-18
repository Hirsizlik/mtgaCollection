package hirsizlik.mtgacollection.mapper;

import java.util.function.Function;

/**
 * a FunctionInterface which takes a parameter, maps it and packs it into a MappingResult.
 *
 * @param <T> the parameter type
 * @param <R> the return type, packed into a MappingResult&lt;T, R>
 * @see Function
 * @see MappingResult
 */
@FunctionalInterface
public interface Mapper<T, R> extends Function<T, MappingResult<T, R>> {
	/**
	 * Bypasses the MappingResult, returning the result (or Exception) immediately.
	 * @param input the input to be mapped
	 * @throws MappingException if a error occured during mapping
	 * @return the mapped object
	 */
	default R applyDirect(final T input) {
		return apply(input).get();
	}
}
