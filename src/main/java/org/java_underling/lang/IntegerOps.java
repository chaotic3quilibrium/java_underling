package org.java_underling.lang;

import org.java_underling.util.ArraysOps;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class providing static methods to create and work with {@link Integer} instances.
 */
public class IntegerOps {

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

  @NotNull
  public static List<Integer> findSetBitIndicesAsList(int bits) {
    if (bits == 0) {
      return List.of();
    }

    return Arrays.stream(findSetBitIndices(bits))
        .boxed()
        .toList();
  }

  @NotNull
  public static Set<Integer> findSetBitIndicesAsSet(int bits) {
    if (bits == 0) {
      return Set.of();
    }

    return Arrays.stream(findSetBitIndices(bits))
        .boxed()
        .collect(Collectors.toUnmodifiableSet());
  }
}
