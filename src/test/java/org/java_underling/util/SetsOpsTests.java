package org.java_underling.util;

import org.java_underling.util.SetsOps.SetPairViewKey;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetsOpsTests {
  //TODO: fill in with all the remaining tests (add unimplemented items)
  //  - ???

  private <T> void validateContrastSetPairMap(
      Map<SetPairViewKey, Set<T>> expectedTsBySetPairViewKey,
      Map<SetPairViewKey, Set<T>> actualTsBySetPairViewKey
  ) {
    assertEquals(expectedTsBySetPairViewKey, actualTsBySetPairViewKey);
    assertTrue(CollectionsOps.isUnmodifiable(actualTsBySetPairViewKey));
    actualTsBySetPairViewKey.values()
        .forEach(ts ->
            assertTrue(CollectionsOps.isUnmodifiable(ts)));
  }

  @Test
  public void testContrastSetPair() {
    var setA = Set.of(1, 2, 3);
    var setB = Set.of(2, 3, 4);
    validateContrastSetPairMap(
        Map.of(
            SetPairViewKey.UNION, Set.of(),
            SetPairViewKey.LEFT, Set.of(),
            SetPairViewKey.RIGHT, Set.of(),
            SetPairViewKey.INTERSECTION, Set.of(),
            SetPairViewKey.LEFT_DIFFERENCE, Set.of(),
            SetPairViewKey.RIGHT_DIFFERENCE, Set.of(),
            SetPairViewKey.DIFFERENCE, Set.of()),
        SetsOps.contrastSetPair(Set.of(), Set.of()));
    validateContrastSetPairMap(
        Map.of(
            SetPairViewKey.UNION, setA,
            SetPairViewKey.LEFT, setA,
            SetPairViewKey.RIGHT, Set.of(),
            SetPairViewKey.INTERSECTION, Set.of(),
            SetPairViewKey.LEFT_DIFFERENCE, setA,
            SetPairViewKey.RIGHT_DIFFERENCE, Set.of(),
            SetPairViewKey.DIFFERENCE, setA),
        SetsOps.contrastSetPair(setA, Set.of()));
    validateContrastSetPairMap(
        Map.of(
            SetPairViewKey.UNION, setA,
            SetPairViewKey.LEFT, Set.of(),
            SetPairViewKey.RIGHT, setA,
            SetPairViewKey.INTERSECTION, Set.of(),
            SetPairViewKey.LEFT_DIFFERENCE, Set.of(),
            SetPairViewKey.RIGHT_DIFFERENCE, setA,
            SetPairViewKey.DIFFERENCE, setA),
        SetsOps.contrastSetPair(Set.of(), setA));
    validateContrastSetPairMap(
        Map.of(
            SetPairViewKey.UNION, Set.of(1, 2, 3, 4),
            SetPairViewKey.LEFT, setA,
            SetPairViewKey.RIGHT, setB,
            SetPairViewKey.INTERSECTION, Set.of(2, 3),
            SetPairViewKey.LEFT_DIFFERENCE, Set.of(1),
            SetPairViewKey.RIGHT_DIFFERENCE, Set.of(4),
            SetPairViewKey.DIFFERENCE, Set.of(1, 4)),
        SetsOps.contrastSetPair(setA, setB));
    validateContrastSetPairMap(
        Map.of(
            SetPairViewKey.UNION, Set.of(1, 2, 3, 4),
            SetPairViewKey.LEFT, setB,
            SetPairViewKey.RIGHT, setA,
            SetPairViewKey.INTERSECTION, Set.of(2, 3),
            SetPairViewKey.LEFT_DIFFERENCE, Set.of(4),
            SetPairViewKey.RIGHT_DIFFERENCE, Set.of(1),
            SetPairViewKey.DIFFERENCE, Set.of(1, 4)),
        SetsOps.contrastSetPair(setB, setA));
  }
}
