package org.java_underling.util.refined;

import org.java_underling.lang.ParametersValidationException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class NonEmptySetTests {
  @Test
  public void testDefaultConstructor() {
    assertEquals(Set.of(1), new NonEmptySet<>(Set.of(1)).set());
    assertThrows(
        ParametersValidationException.class,
        () ->
            new NonEmptySet<>(Set.of()));
  }

  @Test
  public void testValidate() {
    assertTrue(NonEmptySet.validate(Set.of(1)).isEmpty());
    assertTrue(NonEmptySet.validate(Set.of()).isPresent());
  }

  @Test
  public void testFrom() {
    var errorOrValue = NonEmptySet.from(Set.of(1));
    assertTrue(errorOrValue.isRight());
    assertEquals(Set.of(1), errorOrValue.getRight().set());
    assertTrue(NonEmptySet.from(Set.of()).isLeft());
  }
}
