package org.java_underling.lang;

import org.junit.jupiter.api.Test;

public class MissingImplementationExceptionTests {
  @Test
  public void testConstructorMessage() {
    throw new MissingImplementationException();
  }

  @Test
  public void testConstructorFull() {
    throw new MissingImplementationException();
  }
}
