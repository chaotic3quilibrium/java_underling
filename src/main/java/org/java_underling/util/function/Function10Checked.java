package org.java_underling.util.function;

import java.util.Objects;

/**
 * Enables the providing of a {@link Function10} Lambda function which can throw a checked exception.
 */
@FunctionalInterface
public interface Function10Checked<A, B, C, D, E, F, G, H, I, J, R, EX extends Exception> {

  /**
   * Returns the result of applying this function to the provided arguments.
   *
   * @param a the first function argument
   * @param b the second function argument
   * @param c the third function argument
   * @param d the fourth function argument
   * @param e the fifth function argument
   * @param f the sixth function argument
   * @param g the seventh function argument
   * @param h the eighth function argument
   * @param i the ninth function argument
   * @param j the tenth function argument
   * @return the result of applying this function to the provided arguments
   */
  R apply(
      A a,
      B b,
      C c,
      D d,
      E e,
      F f,
      G g,
      H h,
      I i,
      J j) throws EX;

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
  default <V> Function10Checked<A, B, C, D, E, F, G, H, I, J, V, EX> andThen(FunctionChecked<? super R, ? extends V, ? extends EX> after) {
    Objects.requireNonNull(after);

    return (A a, B b, C c, D d, E e, F f, G g, H h, I i, J j) ->
        after.apply(apply(a, b, c, d, e, f, g, h, i, j));
  }
}
