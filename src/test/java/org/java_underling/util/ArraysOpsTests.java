package org.java_underling.util;

import org.java_underling.lang.MissingImplementationException;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ArraysOpsTests {

  @Test
  public void testFindSetBitIndices() {
    throw new MissingImplementationException();
  }

  @Test
  public void testToDistinctSortedArrayInt() {
    var arrayFromInt = ArraysOps.toDistinctSortedArrayInt(Stream.of(1, 3, 2, 6, 6, 5, 7, 8, 9, 4, 0, 1));
    var arrayExpected = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    assertArrayEquals(arrayExpected, arrayFromInt);
    var arrayFromString = ArraysOps.toDistinctSortedArrayInt(Stream.of("1", "3", "2", "6", "6", "5", "7", "8", "9", "4", "0", "1"), Integer::parseInt);
    assertArrayEquals(arrayExpected, arrayFromString);
  }
}
