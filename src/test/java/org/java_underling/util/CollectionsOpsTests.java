package org.java_underling.util;

import org.java_underling.lang.MissingImplementationException;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionsOpsTests {
  @Test
  public void testIsUnmodifiableCollection() {
    assertTrue(CollectionsOps.isUnmodifiable(List.of()));
    assertTrue(CollectionsOps.isUnmodifiable(Set.of()));
    assertFalse(CollectionsOps.isUnmodifiable(new ArrayList<>()));
    assertFalse(CollectionsOps.isUnmodifiable(new HashSet<>()));
  }

  @Test
  public void testIsUnmodifiableMap() {
    assertTrue(CollectionsOps.isUnmodifiable(Map.of()));
    assertFalse(CollectionsOps.isUnmodifiable(new HashMap<>()));
  }

  @Test
  public void testDefensiveCopyToListUnmodifiableNonNulls() {
    throw new MissingImplementationException();
  }

  @Test
  public void testDefensiveCopyToSetUnmodifiableNonNulls() {
    throw new MissingImplementationException();
  }

  @Test
  public void testDefensiveCopyToSetOrderedUnmodifiableNonNulls() {
    throw new MissingImplementationException();
  }

  @Test
  public void testDefensiveCopyToMapUnmodifiableNonNulls() {
    throw new MissingImplementationException();
  }

  @Test
  public void testDefensiveCopyToMapOrderedUnmodifiableNonNulls() {
    throw new MissingImplementationException();
  }

  @Test
  public void testToDistinctSortedArrayInt() {
    var arrayFromInt = CollectionsOps.toDistinctSortedArrayInt(List.of(1, 3, 2, 6, 6, 5, 7, 8, 9, 4, 0, 1));
    var arrayExpected = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    assertArrayEquals(arrayExpected, arrayFromInt);
    var arrayFromString = CollectionsOps.toDistinctSortedArrayInt(List.of("1", "3", "2", "6", "6", "5", "7", "8", "9", "4", "0", "1"), Integer::parseInt);
    assertArrayEquals(arrayExpected, arrayFromString);
  }
}
