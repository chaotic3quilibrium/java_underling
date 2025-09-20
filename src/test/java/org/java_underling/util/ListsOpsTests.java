package org.java_underling.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListsOpsTests {
  @Test
  public void testNullToEmpty() {
    var listEmptyNull = ListsOps.nullToEmpty(null);
    assertNotNull(listEmptyNull);
    assertTrue(listEmptyNull.isEmpty());
    assertTrue(CollectionsOps.isUnmodifiable(listEmptyNull));
    var listEmptySetOf = ListsOps.nullToEmpty(List.of());
    assertNotNull(listEmptySetOf);
    assertTrue(listEmptySetOf.isEmpty());
    assertTrue(CollectionsOps.isUnmodifiable(listEmptySetOf));
    var listEmptySetOf1 = ListsOps.nullToEmpty(List.of(1));
    assertNotNull(listEmptySetOf1);
    assertFalse(listEmptySetOf1.isEmpty());
    assertTrue(CollectionsOps.isUnmodifiable(listEmptySetOf1));
    assertEquals(List.of(1), listEmptySetOf1);
  }

  @Test
  public void testAppendItem() {
    var listA = ListsOps.appendItem(List.of(1), 2);
    assertTrue(CollectionsOps.isUnmodifiable(listA));
    assertEquals(List.of(1, 2), listA);
    var listB = ListsOps.appendItem(listA, 3);
    assertTrue(CollectionsOps.isUnmodifiable(listB));
    assertEquals(List.of(1, 2, 3), listB);
  }

  @Test
  public void testAppendSets() {
    var listAppendA = ListsOps.appendLists(List.of(1), List.of(2, 3), List.of(4));
    assertTrue(CollectionsOps.isUnmodifiable(listAppendA));
    assertEquals(List.of(1, 2, 3, 4), listAppendA);
    var listContainingNull = new ArrayList<Integer>();
    listContainingNull.add(null);
    listContainingNull.add(7);
    var listAppendB = ListsOps.appendLists(listAppendA, List.of(4, 5, 6), listContainingNull);
    assertTrue(CollectionsOps.isUnmodifiable(listAppendB));
    assertEquals(List.of(1, 2, 3, 4, 4, 5, 6, 7), listAppendB);
  }

  @Test
  public void testToDistinctSortedListInteger() {
    assertEquals(List.of(1, 2, 3, 4), ListsOps.toDistinctSortedListInteger(List.of(4, 1, 2, 3)));
    assertEquals(List.of(1, 2, 3, 4), ListsOps.toDistinctSortedListInteger(List.of("4", "1", "2", "3"), Integer::valueOf));
  }
}
