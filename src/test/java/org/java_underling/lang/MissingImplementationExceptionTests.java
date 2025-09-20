package org.java_underling.lang;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MissingImplementationExceptionTests {
  @Test
  public void testConstructorMessage() {
    var missingImplementationException = new MissingImplementationException("test");
    assertEquals("test", missingImplementationException.getMessage());
  }

  @Test
  public void testConstructorAll() {
    var cause = new IOException("test");
    var missingImplementationException = new MissingImplementationException("test", cause, false, false);
    assertEquals("test", missingImplementationException.getMessage());
    assertEquals(cause, missingImplementationException.getCause());
  }
}
