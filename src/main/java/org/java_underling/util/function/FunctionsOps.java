package org.java_underling.util.function;

import org.java_underling.lang.WrappedCheckedException;
import org.java_underling.util.Either;
import org.jetbrains.annotations.NotNull;

import java.util.function.*;
import java.util.stream.Stream;

/**
 * Extends and enhances Java's Lambda Library.
 * <p>
 * ---
 * <p>
 * Useful Reference: <a
 * href="https://docs.google.com/spreadsheets/d/1HzSpbHhaB6igjkc0me2hTYehgfNkYCENw7io7IQI9vM/edit?usp=sharing">Java
 * Lambda Reference Table</a>
 */
public class FunctionsOps {

  private FunctionsOps() {
    throw new UnsupportedOperationException("suppressing class instantiation");
  }

  /**
   * A universal and immutable FunctionalInterface instance of VoidSupplier.
   */
  @NotNull
  public static final VoidSupplier NO_OP = () -> {
  };

  /**
   * A universal and immutable FunctionalInterface instance of VoidSupplierCheckedException.
   */
  @NotNull
  public static final VoidSupplierCheckedException NO_OP_CHECKED_EXCEPTION = () -> {
  };

  /**
   * A simple way to apply a side-effecting (optionally checked-exception) function n number of times. Uses
   * {@code wrapCheckedException} to convert all non-{@link RuntimeException}s into a {@link WrappedCheckedException}.
   *
   * @param nTimes                                              number of times to apply the side-effecting function
   * @param justDoItWithNoInputParametersAndThenIgnoreTheResult side-effecting(optionally checked-exception) function to
   *                                                            apply
   */
  public static void executeSideEffectNTimes(
      int nTimes,
      @NotNull VoidSupplierCheckedException justDoItWithNoInputParametersAndThenIgnoreTheResult
  ) {
    Stream
        .generate(() -> true)
        .limit(nTimes)
        .forEach(ignored ->
            wrapCheckedException(justDoItWithNoInputParametersAndThenIgnoreTheResult).execute());
  }

  /**
   * Returns a {@link VoidSupplier} that wraps the checked exception lambda, {@code voidSupplierCheckedException}, with
   * a {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   * operations.
   *
   * @param voidSupplierCheckedException the lambda which may throw a checked exception that needs to be wrapped with a
   *                                     {@link RuntimeException}
   * @return a {@link VoidSupplier} that wraps the checked exception lambda, {@code voidSupplierCheckedException}, with
   *     a {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   *     operations
   */
  @NotNull
  public static VoidSupplier wrapCheckedException(
      @NotNull VoidSupplierCheckedException voidSupplierCheckedException
  ) {
    return wrapCheckedException(voidSupplierCheckedException, WrappedCheckedException::new);
  }

  /**
   * Returns a {@link VoidSupplier} that wraps the checked exception lambda, {@code voidSupplierCheckedException}, with
   * a {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the lambda
   * within {@link Stream} operations.
   *
   * @param voidSupplierCheckedException the lambda which may throw a checked exception that needs to be wrapped with a
   *                                     {@link RuntimeException}
   * @param fRuntimeExceptionWrapper     the supplier of the RuntimeException descendant instance within which to wrap
   *                                     the checked exception, if thrown
   * @param <E>                          the type of the RuntimeException descendant instance within which to wrap the
   *                                     checked exception, if thrown
   * @return a {@link VoidSupplier} that wraps the checked exception lambda, {@code voidSupplierCheckedException}, with
   *     a {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the
   *     lambda within {@link Stream} operations
   */
  @NotNull
  public static <E extends RuntimeException> VoidSupplier wrapCheckedException(
      @NotNull VoidSupplierCheckedException voidSupplierCheckedException,
      @NotNull Function<Exception, E> fRuntimeExceptionWrapper
  ) {
    return () -> {
      try {
        voidSupplierCheckedException.execute();
      } catch (Exception exception) {
        throw fRuntimeExceptionWrapper.apply(exception);
      }
    };
  }

  /**
   * Returns a {@link Supplier} that wraps the checked exception lambda, {@code supplierCheckedExceptionT}, with a
   * {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   * operations.
   *
   * @param supplierCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with a
   *                                  {@link RuntimeException}
   * @param <T>                       the type of the result returned by the supplier
   * @return a {@link Supplier} that wraps the checked exception lambda, {@code supplierCheckedExceptionT}, with a
   *     {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   *     operations
   */
  @NotNull
  public static <T> Supplier<T> wrapCheckedException(
      @NotNull SupplierCheckedException<T> supplierCheckedExceptionT
  ) {
    return wrapCheckedException(supplierCheckedExceptionT, WrappedCheckedException::new);
  }

  /**
   * Returns a {@link Supplier} that wraps the checked exception lambda, {@code supplierCheckedExceptionT}, with a
   * {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the lambda
   * within {@link Stream} operations.
   *
   * @param supplierCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with a
   *                                  {@link RuntimeException}
   * @param fRuntimeExceptionWrapper  the supplier of the RuntimeException descendant instance within which to wrap the
   *                                  checked exception, if thrown
   * @param <E>                       the type of the RuntimeException descendant instance within which to wrap the
   *                                  checked exception, if thrown
   * @param <T>                       the type of the result returned by the supplier
   * @return a {@link Supplier} that wraps the checked exception lambda, {@code supplierCheckedExceptionT}, with a
   *     {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the
   *     lambda within {@link Stream} operations
   */
  @NotNull
  public static <E extends RuntimeException, T> Supplier<T> wrapCheckedException(
      @NotNull SupplierCheckedException<T> supplierCheckedExceptionT,
      @NotNull Function<Exception, E> fRuntimeExceptionWrapper
  ) {
    return () ->
        Either.tryCatchChecked(supplierCheckedExceptionT)
            .mapLeft(fRuntimeExceptionWrapper)
            .getRightOrThrowLeft();
  }

  /**
   * Returns a {@link Consumer} that wraps the checked exception lambda, {@code consumerCheckedExceptionT}, with a
   * {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   * operations.
   *
   * @param consumerCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with a
   *                                  {@link RuntimeException}
   * @param <T>                       the type of the parameter passed by the consumer
   * @return a {@link Consumer} that wraps the checked exception lambda, {@code consumerCheckedExceptionT}, with a
   *     {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   *     operations
   */
  @NotNull
  public static <T> Consumer<T> wrapCheckedException(
      @NotNull ConsumerCheckedException<T> consumerCheckedExceptionT
  ) {
    return wrapCheckedException(consumerCheckedExceptionT, WrappedCheckedException::new);
  }

  /**
   * Returns a {@link Consumer} that wraps the checked exception lambda, {@code consumerCheckedExceptionT}, with a
   * {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the lambda
   * within {@link Stream} operations.
   *
   * @param consumerCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with a
   *                                  {@link RuntimeException}
   * @param fRuntimeExceptionWrapper  the supplier of the RuntimeException descendant instance within which to wrap the
   *                                  checked exception, if thrown
   * @param <E>                       the type of the RuntimeException descendant instance within which to wrap the
   *                                  checked exception, if thrown
   * @param <T>                       the type of the parameter passed by the consumer
   * @return a {@link Consumer} that wraps the checked exception lambda, {@code consumerCheckedExceptionT}, with a
   *     {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the
   *     lambda within {@link Stream} operations
   */
  @NotNull
  public static <E extends RuntimeException, T> Consumer<T> wrapCheckedException(
      @NotNull ConsumerCheckedException<T> consumerCheckedExceptionT,
      @NotNull Function<Exception, E> fRuntimeExceptionWrapper
  ) {
    return (t) -> {
      try {
        consumerCheckedExceptionT.accept(t);
      } catch (Exception exception) {
        throw fRuntimeExceptionWrapper.apply(exception);
      }
    };
  }

  /**
   * Returns a {@link BiConsumer} that wraps the checked exception lambda, {@code biConsumerCheckedExceptionT}, with a
   * {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   * operations.
   *
   * @param biConsumerCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with a
   *                                    {@link RuntimeException}
   * @param <T>                         the type of the first parameter passed by the bi-consumer
   * @param <U>                         the type of the second parameter passed by the bi-consumer
   * @return a {@link BiConsumer} that wraps the checked exception lambda, {@code biConsumerCheckedExceptionT}, with a
   *     {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   *     operations
   */
  @NotNull
  public static <T, U> BiConsumer<T, U> wrapCheckedException(
      @NotNull BiConsumerCheckedException<T, U> biConsumerCheckedExceptionT
  ) {
    return wrapCheckedException(biConsumerCheckedExceptionT, WrappedCheckedException::new);
  }

  /**
   * Returns a {@link BiConsumer} that wraps the checked exception lambda, {@code biConsumerCheckedExceptionT}, with a
   * {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the lambda
   * within {@link Stream} operations.
   *
   * @param biConsumerCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with a
   *                                    {@link RuntimeException}
   * @param fRuntimeExceptionWrapper    the supplier of the RuntimeException descendant instance within which to wrap
   *                                    the checked exception, if thrown
   * @param <E>                         the type of the RuntimeException descendant instance within which to wrap the
   *                                    checked exception, if thrown
   * @param <T>                         the type of the first parameter passed by the bi-consumer
   * @param <U>                         the type of the second parameter passed by the bi-consumer
   * @return a {@link Consumer} that wraps the checked exception lambda, {@code consumerCheckedExceptionT}, with a
   *     {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the
   *     lambda within {@link Stream} operations
   */
  @NotNull
  public static <E extends RuntimeException, T, U> BiConsumer<T, U> wrapCheckedException(
      @NotNull BiConsumerCheckedException<T, U> biConsumerCheckedExceptionT,
      @NotNull Function<Exception, E> fRuntimeExceptionWrapper
  ) {
    return (t, u) -> {
      try {
        biConsumerCheckedExceptionT.accept(t, u);
      } catch (Exception exception) {
        throw fRuntimeExceptionWrapper.apply(exception);
      }
    };
  }

  /**
   * Returns a {@link Predicate} that wraps the checked exception lambda, {@code predicateCheckedExceptionT}, with a
   * {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   * operations.
   *
   * @param predicateCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with a
   *                                   {@link RuntimeException}
   * @param <T>                        the type of the parameter passed by the predicate
   * @return a {@link Predicate} that wraps the checked exception lambda, {@code predicateCheckedExceptionT}, with a
   *     {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   *     operations
   */
  @NotNull
  public static <T> Predicate<T> wrapCheckedException(
      @NotNull PredicateCheckedException<T> predicateCheckedExceptionT
  ) {
    return wrapCheckedException(predicateCheckedExceptionT, WrappedCheckedException::new);
  }

  /**
   * Returns a {@link Predicate} that wraps the checked exception lambda, {@code predicateCheckedExceptionT}, with a
   * {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the lambda
   * within {@link Stream} operations.
   *
   * @param predicateCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with a
   *                                   {@link RuntimeException}
   * @param fRuntimeExceptionWrapper   the supplier of the RuntimeException descendant instance within which to wrap the
   *                                   checked exception, if thrown
   * @param <E>                        the type of the RuntimeException descendant instance within which to wrap the
   *                                   checked exception, if thrown
   * @param <T>                        the type of the parameter passed by the predicate
   * @return a {@link Predicate} that wraps the checked exception lambda, {@code predicateCheckedExceptionT}, with a
   *     {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the
   *     lambda within {@link Stream} operations
   */
  @NotNull
  public static <E extends RuntimeException, T> Predicate<T> wrapCheckedException(
      @NotNull PredicateCheckedException<T> predicateCheckedExceptionT,
      @NotNull Function<Exception, E> fRuntimeExceptionWrapper
  ) {
    return (t) ->
        Either.tryCatchChecked(() ->
                predicateCheckedExceptionT.test(t))
            .mapLeft(fRuntimeExceptionWrapper)
            .getRightOrThrowLeft();
  }

  /**
   * Returns a {@link UnaryOperator} that wraps the checked exception lambda, {@code unaryCheckedExceptionT}, with a
   * {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   * operations.
   *
   * @param unaryCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with a
   *                               {@link RuntimeException}
   * @param <T>                    the type of the parameter passed into, and returned by the unary operator
   * @return a {@link UnaryOperator} that wraps the checked exception lambda, {@code unaryCheckedExceptionT}, with a
   *     {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   *     operations
   */
  @NotNull
  public static <T> UnaryOperator<T> wrapCheckedException(
      @NotNull UnaryOperatorCheckedException<T> unaryCheckedExceptionT
  ) {
    return wrapCheckedException(unaryCheckedExceptionT, WrappedCheckedException::new);
  }

  /**
   * Returns a {@link UnaryOperator} that wraps the checked exception lambda, {@code unaryCheckedExceptionT}, with a
   * {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the lambda
   * within {@link Stream} operations.
   *
   * @param unaryCheckedExceptionT   the lambda which may throw a checked exception that needs to be wrapped with a
   *                                 {@link RuntimeException}
   * @param fRuntimeExceptionWrapper the supplier of the RuntimeException descendant instance within which to wrap the
   *                                 checked exception, if thrown
   * @param <E>                      the type of the RuntimeException descendant instance within which to wrap the
   *                                 checked exception, if thrown
   * @param <T>                      the type of the parameter passed into, and returned by the unary operator
   * @return a {@link UnaryOperator} that wraps the checked exception lambda, {@code unaryCheckedExceptionT}, with a
   *     {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the
   *     lambda within {@link Stream} operations
   */
  @NotNull
  public static <E extends RuntimeException, T> UnaryOperator<T> wrapCheckedException(
      @NotNull UnaryOperatorCheckedException<T> unaryCheckedExceptionT,
      @NotNull Function<Exception, E> fRuntimeExceptionWrapper
  ) {
    return (t) ->
        Either.tryCatchChecked(() ->
                unaryCheckedExceptionT.apply(t))
            .mapLeft(fRuntimeExceptionWrapper)
            .getRightOrThrowLeft();
  }

  /**
   * Returns a {@link BinaryOperator} that wraps the checked exception lambda, {@code binaryOperatorCheckedExceptionT},
   * with a {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within
   * {@link Stream} operations.
   *
   * @param binaryOperatorCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with
   *                                        a {@link RuntimeException}
   * @param <T>                             the type of the two parameters passed into and the result returned by the
   *                                        binary operator
   * @return a {@link BinaryOperator} that wraps the checked exception lambda, {@code binaryOperatorCheckedExceptionT},
   *     with a {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within
   *     {@link Stream} operations
   */
  @NotNull
  public static <T> BinaryOperator<T> wrapCheckedException(
      @NotNull BinaryOperatorCheckedException<T> binaryOperatorCheckedExceptionT
  ) {
    return wrapCheckedException(binaryOperatorCheckedExceptionT, WrappedCheckedException::new);
  }

  /**
   * Returns a {@link BinaryOperator} that wraps the checked exception lambda, {@code binaryOperatorCheckedExceptionT},
   * with a {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the
   * lambda within {@link Stream} operations.
   *
   * @param binaryOperatorCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with
   *                                        a {@link RuntimeException}
   * @param fRuntimeExceptionWrapper        the supplier of the RuntimeException descendant instance within which to
   *                                        wrap the checked exception, if thrown
   * @param <E>                             the type of the RuntimeException descendant instance within which to wrap
   *                                        the checked exception, if thrown
   * @param <T>                             the type of the two parameters passed into and the result returned by the
   *                                        binary operator
   * @return a {@link BinaryOperator} that wraps the checked exception lambda, {@code binaryOperatorCheckedExceptionT},
   *     with a {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of
   *     the lambda within {@link Stream} operations
   */
  @NotNull
  public static <E extends RuntimeException, T> BinaryOperator<T> wrapCheckedException(
      @NotNull BinaryOperatorCheckedException<T> binaryOperatorCheckedExceptionT,
      @NotNull Function<Exception, E> fRuntimeExceptionWrapper
  ) {
    return (t1, t2) ->
        Either.tryCatchChecked(() ->
                binaryOperatorCheckedExceptionT.apply(t1, t2))
            .mapLeft(fRuntimeExceptionWrapper)
            .getRightOrThrowLeft();
  }

  /**
   * Returns a {@link Function} that wraps the checked exception lambda, {@code functionCheckedExceptionT}, with a
   * {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   * operations.
   *
   * @param functionCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with a
   *                                  {@link RuntimeException}
   * @param <T>                       the type of the parameter passed into the function
   * @param <R>                       the type of the result returned by the function
   * @return a {@link Function} that wraps the checked exception lambda, {@code functionCheckedExceptionT}, with a
   *     {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   *     operations
   */
  @NotNull
  public static <T, R> Function<T, R> wrapCheckedException(
      @NotNull FunctionCheckedException<T, R> functionCheckedExceptionT
  ) {
    return wrapCheckedException(functionCheckedExceptionT, WrappedCheckedException::new);
  }

  /**
   * Returns a {@link Function} that wraps the checked exception lambda, {@code functionCheckedExceptionT}, with a
   * {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the lambda
   * within {@link Stream} operations.
   *
   * @param functionCheckedExceptionT the lambda which may throw a checked exception that needs to be wrapped with a
   *                                  {@link RuntimeException}
   * @param fRuntimeExceptionWrapper  the supplier of the RuntimeException descendant instance within which to wrap the
   *                                  checked exception, if thrown
   * @param <E>                       the type of the RuntimeException descendant instance within which to wrap the
   *                                  checked exception, if thrown
   * @param <T>                       the type of the parameter passed into the function
   * @param <R>                       the type of the result returned by the function
   * @return a {@link Function} that wraps the checked exception lambda, {@code functionCheckedExceptionT}, with a
   *     {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the
   *     lambda within {@link Stream} operations
   */
  @NotNull
  public static <E extends RuntimeException, T, R> Function<T, R> wrapCheckedException(
      @NotNull FunctionCheckedException<T, R> functionCheckedExceptionT,
      @NotNull Function<Exception, E> fRuntimeExceptionWrapper
  ) {
    return (t) ->
        Either.tryCatchChecked(() ->
                functionCheckedExceptionT.apply(t))
            .mapLeft(fRuntimeExceptionWrapper)
            .getRightOrThrowLeft();
  }

  /**
   * Returns a {@link BiFunction} that wraps the checked exception lambda, {@code biFunctionCheckedExceptionTAndU}, with
   * a {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   * operations.
   *
   * @param biFunctionCheckedExceptionTAndU the lambda which may throw a checked exception that needs to be wrapped with
   *                                        a {@link RuntimeException}
   * @param <T>                             the type of the first parameter passed into the function
   * @param <U>                             the type of the second parameter passed into the function
   * @param <R>                             the type of the result returned by the function
   * @return a {@link BiFunction} that wraps the checked exception lambda, {@code biFunctionCheckedExceptionT}, with a
   *     {@link RuntimeException} of {@link WrappedCheckedException} to enable use of the lambda within {@link Stream}
   *     operations
   */
  @NotNull
  public static <T, U, R> BiFunction<T, U, R> wrapCheckedException(
      @NotNull BiFunctionCheckedException<T, U, R> biFunctionCheckedExceptionTAndU
  ) {
    return wrapCheckedException(biFunctionCheckedExceptionTAndU, WrappedCheckedException::new);
  }

  /**
   * Returns a {@link BiFunction} that wraps the checked exception lambda, {@code biFunctionCheckedExceptionTAndU}, with
   * a {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the lambda
   * within {@link Stream} operations.
   *
   * @param biFunctionCheckedExceptionTAndU the lambda which may throw a checked exception that needs to be wrapped with
   *                                        a {@link RuntimeException}
   * @param fRuntimeExceptionWrapper        the supplier of the RuntimeException descendant instance within which to
   *                                        wrap the checked exception, if thrown
   * @param <E>                             the type of the RuntimeException descendant instance within which to wrap
   *                                        the checked exception, if thrown
   * @param <T>                             the type of the first parameter passed into the function
   * @param <U>                             the type of the second parameter passed into the function
   * @param <R>                             the type of the result returned by the function
   * @return a {@link BiFunction} that wraps the checked exception lambda, {@code biFunctionCheckedExceptionT}, with a
   *     {@link RuntimeException} returned by the supplier, {@code fRuntimeExceptionWrapper}, to enable use of the
   *     lambda within {@link Stream} operations
   */
  @NotNull
  public static <E extends RuntimeException, T, U, R> BiFunction<T, U, R> wrapCheckedException(
      @NotNull BiFunctionCheckedException<T, U, R> biFunctionCheckedExceptionTAndU,
      @NotNull Function<Exception, E> fRuntimeExceptionWrapper
  ) {
    return (t, u) ->
        Either.tryCatchChecked(() ->
                biFunctionCheckedExceptionTAndU.apply(t, u))
            .mapLeft(fRuntimeExceptionWrapper)
            .getRightOrThrowLeft();
  }
}
