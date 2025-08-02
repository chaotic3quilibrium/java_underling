package org.java_underling.lang;

import org.java_underling.util.function.FunctionsOps;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.util.stream.Stream;

/**
 * {@code WrappedCheckedException} is an <em>unchecked exception</em> final class used to wrap a checked exception
 * (guaranteed to be not {@code null}, and returned by {@link #getCause()}) that can be declared and thrown by a method
 * or constructor's explicitly defined {@code throws} clause.
 * <p>
 * {@link FunctionsOps} uses this to wrap the checked exception lambdas to enable use of the lambda within {@link Stream}
 * operations.
 */
public final class WrappedCheckedException extends RuntimeException {
  @SuppressWarnings("SerialVersionUIDWithWrongSignature")
  @Serial
  static final long serialVersionUID = 596085657495556565L;

  /**
   * Constructs a new runtime exception with the specified detail message and cause.  <p>Note that the detail message
   * associated with {@code cause} is <i>not</i> automatically incorporated in this runtime exception's detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
   * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A {@code null}
   *                value is <i>not</i> permitted.)
   */
  public WrappedCheckedException(
      @NotNull String message,
      @NotNull Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * Constructs a new runtime exception with the specified cause and a detail message of {@code cause.toString())}
   * (which typically contains the class and detail message of {@code cause}).  This constructor is useful for runtime
   * exceptions that are little more than wrappers for other throwables.
   *
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A {@code null}
   *              value is <i>not</i> permitted.)
   */
  public WrappedCheckedException(
      @NotNull Throwable cause
  ) {
    super(cause);
  }

  /**
   * Constructs a new runtime exception with the specified detail message, cause, suppression enabled or disabled, and
   * writable stack trace enabled or disabled.
   *
   * @param message            the detail message.
   * @param cause              the cause.  (A {@code null} value is <i>not</i> permitted.)
   * @param enableSuppression  whether or not suppression is enabled or disabled
   * @param writableStackTrace whether or not the stack trace should be writable
   */
  public WrappedCheckedException(
      @NotNull String message,
      @NotNull Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
