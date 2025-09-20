package org.java_underling.util.refined;

import org.java_underling.lang.ParametersValidationException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NonEmptyMapTests {
  @Test
  public void testDefaultConstructor() {
    assertEquals(Map.of(1, "x"), new NonEmptyMap<>(Map.of(1, "x")).map());
    assertThrows(
        ParametersValidationException.class,
        () ->
            new NonEmptyMap<>(Map.of()));
  }

  @Test
  public void testValidate() {
    assertTrue(NonEmptyMap.validate(Map.of(1, "x")).isEmpty());
    assertTrue(NonEmptyMap.validate(Map.of()).isPresent());
  }

  @Test
  public void testFrom() {
    var errorOrValue = NonEmptyMap.from(Map.of(1, "x"));
    assertTrue(errorOrValue.isRight());
    assertEquals(Map.of(1, "x"), errorOrValue.getRight().map());
    assertTrue(NonEmptyMap.from(Map.of()).isLeft());
  }
}
