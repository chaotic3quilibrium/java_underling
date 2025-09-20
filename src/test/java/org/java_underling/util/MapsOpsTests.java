package org.java_underling.util;

import org.java_underling.lang.MissingImplementationException;
import org.java_underling.lang.ParametersValidationException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MapsOpsTests {
  @Test
  public void testNullToEmpty() {
    var mapEmptyNull = MapsOps.nullToEmpty(null);
    assertNotNull(mapEmptyNull);
    assertTrue(mapEmptyNull.isEmpty());
    assertTrue(CollectionsOps.isUnmodifiable(mapEmptyNull));
    var mapEmptyMapOf = MapsOps.nullToEmpty(Map.of());
    assertNotNull(mapEmptyMapOf);
    assertTrue(mapEmptyMapOf.isEmpty());
    assertTrue(CollectionsOps.isUnmodifiable(mapEmptyMapOf));
    var mapEmptyMapOfIntegerAndString = MapsOps.nullToEmpty(Map.of(1, "x"));
    assertNotNull(mapEmptyMapOfIntegerAndString);
    assertFalse(mapEmptyMapOfIntegerAndString.isEmpty());
    assertTrue(CollectionsOps.isUnmodifiable(mapEmptyMapOfIntegerAndString));
    assertEquals(Map.of(1, "x"), mapEmptyMapOfIntegerAndString);
  }

  @Test
  public void testEntryContainsNulls() {
    var mapEntryNullNull = new HashMap<>();
    mapEntryNullNull.put(null, null);
    var mapEntryA = mapEntryNullNull.entrySet().iterator().next();
    assertNull(mapEntryA.getKey());
    assertNull(mapEntryA.getValue());
    assertEquals(
        Optional.of(
            new ParametersValidationException(
                "MapsOps.containsNulls failed preconditions on the entry",
                List.of(
                    "entry.getKey() is null",
                    "entry.getValue() is null"))),
        MapsOps.containsNulls(mapEntryA));
    var mapEntryNullString = new HashMap<>();
    mapEntryNullString.put(null, "x");
    var mapEntryB = mapEntryNullString.entrySet().iterator().next();
    assertNull(mapEntryB.getKey());
    assertNotNull(mapEntryB.getValue());
    assertEquals("x", mapEntryB.getValue());
    assertEquals(
        Optional.of(
            new ParametersValidationException(
                "MapsOps.containsNulls failed preconditions on the entry",
                List.of(
                    "entry.getKey() is null"))),
        MapsOps.containsNulls(mapEntryB));
    var mapEntryIntegerNull = new HashMap<>();
    mapEntryIntegerNull.put(1, null);
    var mapEntryC = mapEntryIntegerNull.entrySet().iterator().next();
    assertNotNull(mapEntryC.getKey());
    assertNull(mapEntryC.getValue());
    assertEquals(1, mapEntryC.getKey());
    assertEquals(
        Optional.of(
            new ParametersValidationException(
                "MapsOps.containsNulls failed preconditions on the entry",
                List.of(
                    "entry.getValue() is null"))),
        MapsOps.containsNulls(mapEntryC));
  }

  @Test
  public void testAddEntry() {
    throw new MissingImplementationException();
  }

  @Test
  public void testAddKeyAndValue() {
    throw new MissingImplementationException();
  }

  @Test
  public void testAppendEntry() {
    throw new MissingImplementationException();
  }

  @Test
  public void testAppendKeyAndValue() {
    throw new MissingImplementationException();
  }

  @Test
  public void testAddMaps() {
    throw new MissingImplementationException();
  }

  @Test
  public void testAppendMaps() {
    throw new MissingImplementationException();
  }

  @Test
  public void testSwap() {
    throw new MissingImplementationException();
  }

  @Test
  public void testSwapOrdered() {
    throw new MissingImplementationException();
  }
}
