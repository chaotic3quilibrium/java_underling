package org.java_underling.lang;

import org.java_underling.util.CollectionsOps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

/**
 * {@code ParametersValidationException} is an <em>unchecked exception</em> final class designed to collect a set of
 * failed validations during the construction of a class or record.
 * <p>
 * It's intended to facilitate the "validated record instance" pattern that implements the principle of only allowing
 * the creation of instances with a valid state.
 */
public final class ParametersValidationException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = -2463403636851524272L;

  public static final String DEFAULT_MESSAGE = "Parameters validation failed";

  private final List<String> parametersValidationFailureMessages;

  @NotNull
  private static String formatMessage(
      @NotNull String message,
      @NotNull List<String> parametersValidationFailureMessages
  ) {
    return parametersValidationFailureMessages.isEmpty()
        ? message
        : "%s - Parameter Validation Failures: [%s]".formatted(
            message,
            String.join("|", parametersValidationFailureMessages));
  }

  public ParametersValidationException() {
    this(DEFAULT_MESSAGE);
  }

  public ParametersValidationException(
      @NotNull String message
  ) {
    this(message, List.of());
  }

  public ParametersValidationException(
      @NotNull List<String> parametersValidationFailureMessages
  ) {
    this(DEFAULT_MESSAGE, parametersValidationFailureMessages);
  }

  public ParametersValidationException(
      @Nullable Throwable cause
  ) {
    this(DEFAULT_MESSAGE, cause, List.of());
  }

  public ParametersValidationException(
      @NotNull String message,
      @Nullable Throwable cause
  ) {
    super(message, cause);
    this.parametersValidationFailureMessages = List.of();
  }

  public ParametersValidationException(
      @NotNull String message,
      @NotNull String parametersValidationFailureMessage
  ) {
    this(message, List.of(parametersValidationFailureMessage));
  }

  public ParametersValidationException(
      @NotNull String message,
      @NotNull List<String> parametersValidationFailureMessages
  ) {
    super(formatMessage(message, parametersValidationFailureMessages));
    this.parametersValidationFailureMessages = CollectionsOps.defensiveCopyToListUnmodifiableNonNulls(parametersValidationFailureMessages);
  }

  public ParametersValidationException(
      @Nullable Throwable cause,
      @NotNull List<String> parametersValidationFailureMessages
  ) {
    this(
        DEFAULT_MESSAGE,
        cause,
        CollectionsOps.defensiveCopyToListUnmodifiableNonNulls(parametersValidationFailureMessages));
  }

  public ParametersValidationException(
      @NotNull String message,
      @Nullable Throwable cause,
      @NotNull String parametersValidationFailureMessage
  ) {
    this(message, cause, List.of(parametersValidationFailureMessage));
  }

  public ParametersValidationException(
      @NotNull String message,
      @Nullable Throwable cause,
      @NotNull List<String> parametersValidationFailureMessages
  ) {
    super(message, cause);
    this.parametersValidationFailureMessages = CollectionsOps.defensiveCopyToListUnmodifiableNonNulls(parametersValidationFailureMessages);
  }

  public ParametersValidationException(
      @NotNull String message,
      @Nullable Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace,
      @NotNull List<String> parametersValidationFailureMessages
  ) {
    super(
        formatMessage(
            message,
            parametersValidationFailureMessages),
        cause,
        enableSuppression,
        writableStackTrace);
    this.parametersValidationFailureMessages = CollectionsOps.defensiveCopyToListUnmodifiableNonNulls(parametersValidationFailureMessages);
    ;
  }

  /**
   * Return a list consisting of a message for each failed validation.
   *
   * @return a list consisting of a message for each failed validation
   */
  @NotNull
  public List<String> getParametersValidationFailureMessages() {
    return this.parametersValidationFailureMessages;
  }

  @Override
  public boolean equals(Object object) {
    return ((this == object) ||
        ((object instanceof ParametersValidationException that) &&
            Objects.equals(this.parametersValidationFailureMessages, that.parametersValidationFailureMessages)));
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.parametersValidationFailureMessages);
  }
}
