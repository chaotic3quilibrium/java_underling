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
public class IntegersOps {

  private IntegersOps() {
    throw new UnsupportedOperationException("suppressing class instantiation");
  }

  @NotNull
  public static List<Integer> findSetBitIndicesAsList(int bits) {
    if (bits == 0) {
      return List.of();
    }

    return Arrays.stream(ArraysOps.findSetBitIndices(bits))
        .boxed()
        .toList();
  }

  @NotNull
  public static Set<Integer> findSetBitIndicesAsSet(int bits) {
    if (bits == 0) {
      return Set.of();
    }

    return Arrays.stream(ArraysOps.findSetBitIndices(bits))
        .boxed()
        .collect(Collectors.toUnmodifiableSet());
  }
}
