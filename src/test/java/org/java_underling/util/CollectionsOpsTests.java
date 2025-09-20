package org.java_underling.util;

import org.java_underling.lang.MissingImplementationException;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    throw new MissingImplementationException("x2");
  }
}
