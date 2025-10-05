package org.java_underling.lang;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WrappedCheckedExceptionTests {
  @Test
  public void testConstructorMessageAndCause() {
    var cause = new IOException("test");
    var wrappedCheckedException = new WrappedCheckedException("test", cause);
    assertEquals("test", wrappedCheckedException.getMessage());
    assertEquals(cause, wrappedCheckedException.getCause());
  }

  @Test
  public void testConstructorCause() {
    var cause = new IOException("test");
    var wrappedCheckedException = new WrappedCheckedException(cause);
    assertEquals(cause, wrappedCheckedException.getCause());
  }

  @Test
  public void testConstructorAll() {
    var cause = new IOException("test");
    var wrappedCheckedException = new WrappedCheckedException("test", cause, false, false);
    assertEquals("test", wrappedCheckedException.getMessage());
    assertEquals(cause, wrappedCheckedException.getCause());
  }

  @Test
  public void testThrowsIllegalArgumentExceptionOnNullCause() {
    @SuppressWarnings({"ThrowableNotThrown", "DataFlowIssue"})
    var illegalArgumentException = assertThrows(
        IllegalArgumentException.class,
        () ->
            new WrappedCheckedException(null));
    assertEquals(
        "Argument for @NotNull parameter 'cause' of org/java_underling/lang/WrappedCheckedException.<init> must not be null",
        illegalArgumentException.getMessage());
  }
}
