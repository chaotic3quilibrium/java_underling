package org.java_underling.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ListsOpsTests {
  @Test
  public void testNullToEmpty() {
    var listEmptyNull = ListsOps.nullToEmpty(null);
    assertNotNull(listEmptyNull);
    assertTrue(listEmptyNull.isEmpty());
    assertTrue(CollectionsOps.isUnmodifiable(listEmptyNull));
    var listEmptyListOf = ListsOps.nullToEmpty(List.of());
    assertNotNull(listEmptyListOf);
    assertTrue(listEmptyListOf.isEmpty());
    assertTrue(CollectionsOps.isUnmodifiable(listEmptyListOf));
    var listEmptyListOf1 = ListsOps.nullToEmpty(List.of(1));
    assertNotNull(listEmptyListOf1);
    assertFalse(listEmptyListOf1.isEmpty());
    assertTrue(CollectionsOps.isUnmodifiable(listEmptyListOf1));
    assertEquals(List.of(1), listEmptyListOf1);
  }

  @Test
  public void testAppendItem() {
    var listA = ListsOps.appendItem(List.of(1), 2);
    assertTrue(CollectionsOps.isUnmodifiable(listA));
    assertEquals(List.of(1, 2), listA);
    var listB = ListsOps.appendItem(listA, 3);
    assertTrue(CollectionsOps.isUnmodifiable(listB));
    assertEquals(List.of(1, 2, 3), listB);
    var listC = ListsOps.appendItem(List.of(), 10);
    assertEquals(List.of(10), listC);
  }

  @Test
  public void testAppendLists() {
    var listAppendA = ListsOps.appendLists(List.of(1), List.of(2, 3), List.of(4));
    assertTrue(CollectionsOps.isUnmodifiable(listAppendA));
    assertEquals(List.of(1, 2, 3, 4), listAppendA);
    var listContainingNull = new ArrayList<Integer>();
    listContainingNull.add(null);
    listContainingNull.add(7);
    var listAppendB = ListsOps.appendLists(listAppendA, List.of(4, 5, 6), listContainingNull);
    assertTrue(CollectionsOps.isUnmodifiable(listAppendB));
    assertEquals(List.of(1, 2, 3, 4, 4, 5, 6, 7), listAppendB);
    var listAppendC = ListsOps.appendLists(List.of(), null, List.of());
    assertEquals(List.of(), listAppendC);
    var a = new List[]{};
    var listAppendD = ListsOps.appendLists(a);
    assertEquals(List.of(), listAppendD);
  }

  @Test
  public void testToListUnmodifiable() {
    var expectedList = List.of(1, 2, 3);
    var nullContainingList = Arrays.asList(null, 1, null, 2, null, 3, null);
    assertEquals(7, nullContainingList.size());
    var actualList = ListsOps.toListUnmodifiable(nullContainingList.stream());
    assertEquals(expectedList, actualList);
    assertTrue(CollectionsOps.isUnmodifiable(actualList));
  }

  @Test
  public void testToDistinctSortedListInteger() {
    assertEquals(
        List.of(1, 2, 3, 4),
        ListsOps.toDistinctSortedListInteger(
            Stream.of(4, 1, 2, 3)));
    assertEquals(
        List.of(1, 2, 3, 4),
        ListsOps.toDistinctSortedListInteger(
            Stream.of("4", "1", "2", "3"),
            Integer::valueOf));
  }
}
