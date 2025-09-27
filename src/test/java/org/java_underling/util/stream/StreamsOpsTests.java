package org.java_underling.util.stream;

import org.java_underling.util.CollectionsOps;
import org.java_underling.util.MapsOps;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

public class StreamsOpsTests {

  @Test
  public void testFromFactories() {
    var fruitsString = "apple,banana,cantaloupe";
    var fruits = List.of(
        "apple",
        "banana",
        "cantaloupe");
    var fruitsStreamFromIterable = StreamsOps.from(fruits);
    assertEquals(fruitsString, String.join(",", fruitsStreamFromIterable.toList()));
    var fruitsStreamFromIterableParallel = StreamsOps.from(fruits, true);
    assertEquals(fruitsString, String.join(",", fruitsStreamFromIterableParallel.toList()));
    var fruitsStreamFromIterator = StreamsOps.from(fruits.iterator());
    assertEquals(fruitsString, String.join(",", fruitsStreamFromIterator.toList()));
    var fruitsStreamFromIteratorParallel = StreamsOps.from(fruits.iterator(), true);
    assertEquals(fruitsString, String.join(",", fruitsStreamFromIteratorParallel.toList()));
  }

  @Test
  public void testZip() {
    var listEmpty = StreamsOps.zip(Stream.empty(), Stream.empty()).toList();
    assertTrue(listEmpty.isEmpty());
    var listInts0To4 = List.of(0, 1, 2, 3, 4);
    var listStringsX0to2 = List.of("x0", "x1", "x2");
    var listA = StreamsOps.zip(listInts0To4.stream(), listStringsX0to2.stream()).toList();
    assertEquals(List.of(entry(0, "x0"), entry(1, "x1"), entry(2, "x2")), listA);
    var listStringsX0to5 = List.of("x0", "x1", "x2", "x3", "x4", "x5");
    var listB = StreamsOps.zip(listInts0To4.stream(), listStringsX0to5.stream()).toList();
    assertEquals(List.of(entry(0, "x0"), entry(1, "x1"), entry(2, "x2"), entry(3, "x3"), entry(4, "x4")), listB);
    var listInts0To5 = List.of(0, 1, 2, 3, 4, 5);
    var listC = StreamsOps.zip(listInts0To5.stream(), listStringsX0to5.stream()).toList();
    assertEquals(List.of(entry(0, "x0"), entry(1, "x1"), entry(2, "x2"), entry(3, "x3"), entry(4, "x4"), entry(5, "x5")), listC);
  }

  @Test
  public void testZipWithIndex() {
    var listEmpty = StreamsOps.zipWithIndex(Stream.empty()).toList();
    assertTrue(listEmpty.isEmpty());
    var stringAndIndexes = StreamsOps.zipWithIndex(Stream.of("x0", "x1", "x2")).toList();
    assertEquals(List.of(entry("x0", 0), entry("x1", 1), entry("x2", 2)), stringAndIndexes);
  }

  @Test
  public void testToListUnmodifiableNonNulls() {
    var expectedList = List.of(1, 2, 3);
    var nullContainingList = Arrays.asList(null, 1, null, 2, null, 3, null);
    assertEquals(7, nullContainingList.size());
    var actualList = StreamsOps.toListUnmodifiableNonNulls(nullContainingList.stream());
    assertEquals(expectedList, actualList);
    assertTrue(CollectionsOps.isUnmodifiable(actualList));
  }

  @Test
  public void testToSetUnmodifiableNonNulls() {
    var expectedSet = Set.of(3, 2, 1);
    var nullContainingSet = Stream.of(null, 2, null, 1, null, 3, null).collect(Collectors.toSet());
    assertEquals(4, nullContainingSet.size());
    var actualSet = StreamsOps.toSetUnmodifiableNonNulls(nullContainingSet.stream());
    assertEquals(expectedSet, actualSet);
    assertTrue(CollectionsOps.isUnmodifiable(actualSet));
  }

  @Test
  public void testToSetOrderedUnmodifiableNonNulls() {
    var expectedSetOrdered = new LinkedHashSet<>(List.of(3, 2, 1));
    var nullContainingSetOrdered = new LinkedHashSet<>(Stream.of(null, 3, null, 2, null, 1, null).toList());
    assertEquals(4, nullContainingSetOrdered.size());
    assertNull(nullContainingSetOrdered.iterator().next());
    var actualSetOrdered = StreamsOps.toSetOrderedUnmodifiableNonNulls(nullContainingSetOrdered.stream());
    assertEquals(expectedSetOrdered, actualSetOrdered);
    assertEquals(expectedSetOrdered.stream().toList(), actualSetOrdered.stream().toList());
    assertTrue(CollectionsOps.isUnmodifiable(actualSetOrdered));
  }

  @Test
  public void testToMapOrderedUnmodifiableNonNulls() {
    var mapA = new LinkedHashMap<Integer, String>();
    mapA.put(null, "xnull");
    mapA.put(1, "x1");
    mapA.put(-1, null);
    mapA.put(2, "x2");
    @SuppressWarnings("SimplifyStreamApiCallChains")
    var entries = mapA.entrySet().stream().collect(Collectors.toList());
    entries.add(1, null);
    var mapB = StreamsOps.toMapOrderedUnmodifiableNonNulls(entries.stream());
    var list = new ArrayList<String>();
    list.add(null);
    list.add("x1");
    list.add(null);
    list.add("x");
    list.add(null);
    list.add("2");
    list.add("x2");
    var mapC = StreamsOps.toMapOrderedUnmodifiableNonNulls(
        list.stream(),
        string -> {
          if ((string != null) && (string.length() > 1)) {
            return Optional.of(entry(Integer.parseInt(string.substring(1, 2)), string));
          }

          return Optional.empty();
        });
    assertEquals(MapsOps.ofOrdered(1, "x1", 2, "x2"), mapC);
  }
}
