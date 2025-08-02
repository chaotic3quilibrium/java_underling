package org.java_underling.util.function;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Enables the providing of a {@link BiConsumer} Lambda function which can throw a checked exception.
 */
@FunctionalInterface
public interface BiConsumerChecked<T, U, E extends Exception> {

  /**
   * Performs this operation on the given arguments.
   *
   * @param t the first input argument
   * @param u the second input argument
   */
  void accept(T t, U u) throws E;

  /**
   * Returns a composed {@code BiConsumerChecked} that performs, in sequence, this
   * operation followed by the {@code after} operation. If performing either
   * operation throws an exception, it is relayed to the caller of the
   * composed operation.  If performing this operation throws an exception,
   * the {@code after} operation will not be performed.
   *
   * @param after the operation to perform after this operation
   * @return a composed {@code BiConsumerChecked} that performs in sequence this
   * operation followed by the {@code after} operation
   * @throws NullPointerException if {@code after} is null
   */
  default BiConsumerChecked<T, U, E> andThen(BiConsumerChecked<? super T, ? super U, ? extends E> after) {
    Objects.requireNonNull(after);

    return (t, u) -> {
      accept(t, u);
      after.accept(t, u);
    };
  }
}
