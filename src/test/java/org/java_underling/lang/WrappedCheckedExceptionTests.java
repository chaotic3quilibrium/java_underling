package org.java_underling.lang;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
