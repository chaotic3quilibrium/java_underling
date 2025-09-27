package org.java_underling.util;

import org.jetbrains.annotations.NotNull;

import java.util.function.ToIntFunction;
import java.util.stream.Stream;

/**
 * Utility class providing static methods to create and work with array instances.
 */
public class ArraysOps {

  private ArraysOps() {
    throw new UnsupportedOperationException("suppressing class instantiation");
  }

  public static final int[] EMPTY_INT_ARRAY = new int[0];

  public static int[] findSetBitIndices(int bits) {
    if (bits == 0) {
      return ArraysOps.EMPTY_INT_ARRAY;
    }

    int[] bitsIndices = new int[32];
    int size = 0;
    while (bits != 0) {
      int c = bits & -bits;
      int index = Integer.numberOfTrailingZeros(c);
      bitsIndices[size++] = index;
      bits = bits ^ c;
    }
    var result = new int[size];
    System.arraycopy(bitsIndices, 0, result, 0, size);

    return result;
  }

  /**
   * Returns a new {@code int} array from a collection of {@link Integer}s.
   *
   * @param integers the source of the derived {@code int} values
   * @return a new {@code int} array from a collection of {@link Integer}s
   */
  public static int[] toDistinctSortedArrayInt(
      @NotNull Stream<Integer> integers
  ) {
    return toDistinctSortedArrayInt(integers, Integer::intValue);
  }

  /**
   * Returns a new {@code int} array from a collection of {@code ts} deriving the {@code int} value via the function
   * {@code fTToId}.
   *
   * @param ts     the source of the derived {@code int} values
   * @param fTToId the function to use to extract the {@code int} value from an element of the collection
   * @param <T>    the type of instances contained in the collection
   * @return a new {@code int} array from a collection of {@code ts} deriving the {@code int} value via the function
   *     {@code fTToId}
   */
  public static <T> int[] toDistinctSortedArrayInt(
      @NotNull Stream<T> ts,
      @NotNull ToIntFunction<T> fTToId
  ) {
    return ts
        .mapToInt(fTToId)
        .distinct()
        .sorted()
        .toArray();
  }
}
