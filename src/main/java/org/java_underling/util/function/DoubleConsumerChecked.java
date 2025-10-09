package org.java_underling.util.function;

import java.util.Objects;
import java.util.function.DoubleConsumer;

/**
 * Enables the providing of a {@link DoubleConsumer} Lambda function which can throw a checked exception.
 */
@FunctionalInterface
public interface DoubleConsumerChecked<E extends Exception> {

  /**
   * Performs this operation on the given argument.
   *
   * @param value the input argument
   */
  void accept(double value) throws E;

  /**
   * Returns a composed {@code DoubleConsumer} that performs, in sequence, this operation followed by the {@code after}
   * operation. If performing either operation throws an exception, it is relayed to the caller of the composed
   * operation.  If performing this operation throws an exception, the {@code after} operation will not be performed.
   *
   * @param after the operation to perform after this operation
   * @return a composed {@code DoubleConsumer} that performs in sequence this operation followed by the {@code after}
   *     operation
   * @throws NullPointerException if {@code after} is null
   */
  default DoubleConsumerChecked<E> andThen(DoubleConsumerChecked<? extends E> after) {
    Objects.requireNonNull(after);

    return (double t) -> {
      accept(t);
      after.accept(t);
    };
  }
}
