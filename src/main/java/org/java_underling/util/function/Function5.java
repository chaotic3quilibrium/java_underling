package org.java_underling.util.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a function that accepts five arguments, and produces a result.
 * <p>
 * This is the five-arity specialization of {@link Function}.
 *
 * @param <A> the type of the first argument to the function
 * @param <B> the type of the second argument to the function
 * @param <C> the type of the third argument to the function
 * @param <D> the type of the fourth argument to the function
 * @param <E> the type of the fifth argument to the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface Function5<A, B, C, D, E, R> {

  /**
   * Returns the result of applying this function to the provided arguments.
   *
   * @param a the first function argument
   * @param b the second function argument
   * @param c the third function argument
   * @param d the fourth function argument
   * @param e the fifth function argument
   * @return the result of applying this function to the provided arguments
   */
  R apply(
      A a,
      B b,
      C c,
      D d,
      E e);

  /**
   * Returns a composed function that first applies this function to its input, and then applies the {@code after}
   * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
   * composed function.
   *
   * @param <V>   the type of output of the {@code after} function, and of the composed function
   * @param after the function to apply after this function is applied
   * @return a composed function that first applies this function and then applies the {@code after} function
   * @throws NullPointerException if after is null
   */
  default <V> Function5<A, B, C, D, E, V> andThen(Function<? super R, ? extends V> after) {
    Objects.requireNonNull(after);

    return (A a, B b, C c, D d, E e) ->
        after.apply(apply(a, b, c, d, e));
  }
}
