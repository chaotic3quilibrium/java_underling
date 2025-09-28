package org.java_underling.util;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumsIdOpsTests {
  public interface Equivalent {
    int getEquivalent();
  }

  private enum TrafficLightWithId implements Equivalent {
    SGREEN(1),
    SYELLOW(2),
    SRED(5);

    private final int equivalent;

    TrafficLightWithId(int equivalent) {
      this.equivalent = equivalent;
    }

    public int getEquivalent() {
      return this.equivalent;
    }
  }

  @Test
  public void testBaseCase() {
    var tlwix = EnumsIdOps.from(
        TrafficLightWithId.class,
        TrafficLightWithId::getEquivalent,
        Object::toString,
        enumValue ->
            "X%d".formatted(enumValue.name().length()));
    assertEquals("SGREEN(1), SYELLOW(2), SRED(5)", tlwix.join());
    assertEquals(Optional.of(TrafficLightWithId.SGREEN), tlwix.get(1));
    assertEquals(Optional.of(entry(TrafficLightWithId.SGREEN, 1)), tlwix.valueOf("SGREEN"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SGREEN, 1)), tlwix.valueOf("sgreen"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SGREEN, 1)), tlwix.valueOf("sgrEEn"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SGREEN, 1)), tlwix.valueOf("1"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SGREEN, 1)), tlwix.valueOf("X6"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SGREEN, 1)), tlwix.valueOf("x6"));
    assertEquals(1, tlwix.get(TrafficLightWithId.SGREEN));
    assertEquals(Optional.of(TrafficLightWithId.SYELLOW), tlwix.get(2));
    assertEquals(Optional.of(entry(TrafficLightWithId.SYELLOW, 2)), tlwix.valueOf("SYELLOW"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SYELLOW, 2)), tlwix.valueOf("syellow"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SYELLOW, 2)), tlwix.valueOf("sYelLoW"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SYELLOW, 2)), tlwix.valueOf("2"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SYELLOW, 2)), tlwix.valueOf("X7"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SYELLOW, 2)), tlwix.valueOf("x7"));
    assertEquals(2, tlwix.get(TrafficLightWithId.SYELLOW));
    assertEquals(Optional.of(TrafficLightWithId.SRED), tlwix.get(5));
    assertEquals(Optional.of(entry(TrafficLightWithId.SRED, 5)), tlwix.valueOf("SRED"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SRED, 5)), tlwix.valueOf("sred"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SRED, 5)), tlwix.valueOf("sReD"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SRED, 5)), tlwix.valueOf("5"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SRED, 5)), tlwix.valueOf("X4"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SRED, 5)), tlwix.valueOf("x4"));
    assertEquals(5, tlwix.get(TrafficLightWithId.SRED));
    assertEquals(Optional.empty(), tlwix.get(3));
    assertEquals(Optional.empty(), tlwix.valueOf("SBLUE"));
    assertEquals(Optional.empty(), tlwix.valueOf("3"));
    assertEquals(Optional.empty(), tlwix.valueOf("X5"));
  }
}