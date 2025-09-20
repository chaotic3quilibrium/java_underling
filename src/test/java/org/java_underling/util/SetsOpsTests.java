package org.java_underling.util;

import org.java_underling.util.SetsOps.SetPairViewKey;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SetsOpsTests {

  @Test
  public void testNullToEmpty() {
    var setEmptyNull = SetsOps.nullToEmpty(null);
    assertNotNull(setEmptyNull);
    assertTrue(setEmptyNull.isEmpty());
    assertTrue(CollectionsOps.isUnmodifiable(setEmptyNull));
    var setEmptySetOf = SetsOps.nullToEmpty(Set.of());
    assertNotNull(setEmptySetOf);
    assertTrue(setEmptySetOf.isEmpty());
    assertTrue(CollectionsOps.isUnmodifiable(setEmptySetOf));
    var setEmptySetOf1 = SetsOps.nullToEmpty(Set.of(1));
    assertNotNull(setEmptySetOf1);
    assertFalse(setEmptySetOf1.isEmpty());
    assertTrue(CollectionsOps.isUnmodifiable(setEmptySetOf1));
    assertEquals(Set.of(1), setEmptySetOf1);
  }

  @Test
  public void testAddItem() {
    var setEmptyAdd1 = SetsOps.addItem(Set.of(), 1);
    assertEquals(Set.of(1), setEmptyAdd1);
    assertTrue(CollectionsOps.isUnmodifiable(setEmptyAdd1));
    var setAdd2 = SetsOps.addItem(setEmptyAdd1, 2);
    assertEquals(Set.of(1, 2), setAdd2);
    assertTrue(CollectionsOps.isUnmodifiable(setAdd2));
  }

  @Test
  public void testAppendItem() {
    var setA = SetsOps.appendItem(Set.of(1), 2);
    assertTrue(CollectionsOps.isUnmodifiable(setA));
    assertEquals(List.of(1, 2), setA.stream().toList());
    var setB = SetsOps.appendItem(setA, 3);
    assertTrue(CollectionsOps.isUnmodifiable(setB));
    assertEquals(List.of(1, 2, 3), setB.stream().toList());
  }

  @Test
  public void testAddSets() {
    var setAddA = SetsOps.addSets(Set.of(1, 2), Set.of(2, 3));
    assertTrue(CollectionsOps.isUnmodifiable(setAddA));
    assertEquals(Set.of(1, 2, 3), setAddA);
    var setAddB = SetsOps.addSets(setAddA, Set.of(3, 4));
    assertTrue(CollectionsOps.isUnmodifiable(setAddB));
    assertEquals(Set.of(1, 2, 3, 4), setAddB);
    var setContainingNull = new HashSet<Integer>();
    setContainingNull.add(null);
    setContainingNull.add(6);
    var setAddC = SetsOps.addSets(setAddB, Set.of(4, 5), setContainingNull);
    assertTrue(CollectionsOps.isUnmodifiable(setAddC));
    assertEquals(Set.of(1, 2, 3, 4, 5, 6), setAddC);
  }

  @Test
  public void testAppendSets() {
    var setAppendA = SetsOps.appendSets(Set.of(1), Set.of(2), Set.of(3));
    assertTrue(CollectionsOps.isUnmodifiable(setAppendA));
    assertEquals(List.of(1, 2, 3), setAppendA.stream().toList());
    var setContainingNull = new HashSet<Integer>();
    setContainingNull.add(null);
    setContainingNull.add(6);
    var setAppendB = SetsOps.addSets(setAppendA, Set.of(4), Set.of(5), setContainingNull);
    assertTrue(CollectionsOps.isUnmodifiable(setAppendB));
    assertEquals(List.of(1, 2, 3, 4, 5, 6), setAppendB.stream().toList());
  }

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
