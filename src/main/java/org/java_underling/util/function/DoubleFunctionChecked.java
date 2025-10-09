package org.java_underling.util.function;

//public class DoubleFunctionChecked {

import java.util.function.DoubleFunction;

/**
 * Enables the providing of a {@link DoubleFunction} Lambda function which can throw a checked exception.
 */
@FunctionalInterface
public interface DoubleFunctionChecked<R, E extends Exception> {

  /**
   * Applies this function to the given argument.
   *
   * @param value the function argument
   * @return the function result
   */
  R apply(double value) throws E;
}
