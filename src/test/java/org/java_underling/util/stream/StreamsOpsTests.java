package org.java_underling.util.stream;

import org.java_underling.lang.MissingImplementationException;
import org.java_underling.util.CollectionsOps;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    throw new MissingImplementationException();
  }

  @Test
  public void testZipWithIndex() {
    throw new MissingImplementationException();
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
  public void testToSetOrderedUnmodifiable() {
    var setEmpty = StreamsOps.toSetOrderedUnmodifiable(Stream.empty());
    assertEquals(Set.of(), setEmpty);
    var expectedSetOrdered = new LinkedHashSet<>(Stream.of(null, 3, 2, 1).toList());
    var nullContainingSetOrdered = new LinkedHashSet<>(Stream.of(null, 3, null, 2, null, 1, null).toList());
    assertEquals(4, nullContainingSetOrdered.size());
    assertNull(nullContainingSetOrdered.iterator().next());
    var actualSetOrdered = StreamsOps.toSetOrderedUnmodifiable(nullContainingSetOrdered.stream());
    assertEquals(expectedSetOrdered, actualSetOrdered);
    assertEquals(expectedSetOrdered.stream().toList(), actualSetOrdered.stream().toList());
    assertTrue(CollectionsOps.isUnmodifiable(actualSetOrdered));
  }

  @Test
  public void testToMapOrderedUnmodifiableNonNulls() {
    throw new MissingImplementationException();
  }

  @Test
  public void testToMapOrderedUnmodifiable() {
    throw new MissingImplementationException();
  }
}
