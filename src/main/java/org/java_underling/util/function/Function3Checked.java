package org.java_underling.util.function;

import java.util.Objects;

/**
 * Enables the providing of a {@link Function3} Lambda function which can throw a checked exception.
 */
@FunctionalInterface
public interface Function3Checked<A, B, C, R, EX extends Exception> {

  /**
   * Returns the result of applying this function to the provided arguments.
   *
   * @param a the first function argument
   * @param b the second function argument
   * @param c the third function argument
   * @return the result of applying this function to the provided arguments
   */
  R apply(
      A a,
      B b,
      C c) throws EX;

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
  default <V> Function3Checked<A, B, C, V, EX> andThen(FunctionChecked<? super R, ? extends V, ? extends EX> after) {
    Objects.requireNonNull(after);

    return (A a, B b, C c) ->
        after.apply(apply(a, b, c));
  }
}
