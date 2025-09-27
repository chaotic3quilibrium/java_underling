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
    assertEquals(Optional.of(entry(TrafficLightWithId.SGREEN, 1)), tlwix.get("SGREEN"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SGREEN, 1)), tlwix.get("sgreen"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SGREEN, 1)), tlwix.get("sgrEEn"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SGREEN, 1)), tlwix.get("1"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SGREEN, 1)), tlwix.get("X6"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SGREEN, 1)), tlwix.get("x6"));
    assertEquals(1, tlwix.get(TrafficLightWithId.SGREEN));
    assertEquals(Optional.of(TrafficLightWithId.SYELLOW), tlwix.get(2));
    assertEquals(Optional.of(entry(TrafficLightWithId.SYELLOW, 2)), tlwix.get("SYELLOW"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SYELLOW, 2)), tlwix.get("syellow"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SYELLOW, 2)), tlwix.get("sYelLoW"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SYELLOW, 2)), tlwix.get("2"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SYELLOW, 2)), tlwix.get("X7"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SYELLOW, 2)), tlwix.get("x7"));
    assertEquals(2, tlwix.get(TrafficLightWithId.SYELLOW));
    assertEquals(Optional.of(TrafficLightWithId.SRED), tlwix.get(5));
    assertEquals(Optional.of(entry(TrafficLightWithId.SRED, 5)), tlwix.get("SRED"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SRED, 5)), tlwix.get("sred"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SRED, 5)), tlwix.get("sReD"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SRED, 5)), tlwix.get("5"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SRED, 5)), tlwix.get("X4"));
    assertEquals(Optional.of(entry(TrafficLightWithId.SRED, 5)), tlwix.get("x4"));
    assertEquals(5, tlwix.get(TrafficLightWithId.SRED));
    assertEquals(Optional.empty(), tlwix.get(3));
    assertEquals(Optional.empty(), tlwix.get("SBLUE"));
    assertEquals(Optional.empty(), tlwix.get("3"));
    assertEquals(Optional.empty(), tlwix.get("X5"));
  }
}