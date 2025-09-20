package org.java_underling.util;

import org.java_underling.util.stream.StreamsOps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * Utility class providing static methods to create and work with {@link Collection} instances.
 */
public final class CollectionsOps {

  private CollectionsOps() {
    throw new UnsupportedOperationException("suppressing class instantiation");
  }

  /**
   * Returns {@code true} if the {@link Collection} throws an {@link UnsupportedOperationException} when calling
   * {@link Collection#addAll} with {@link Collections#emptyList}, false otherwise.
   *
   * @param collection instance being tested for being unmodifiable
   * @return {@code true} if the {@link Collection} throws an {@link UnsupportedOperationException} when calling
   *     {@link Collection#addAll} with {@link Collections#emptyList}, false otherwise
   */
  public static boolean isUnmodifiable(@NotNull Collection<?> collection) {
    try {
      collection.addAll(Collections.emptyList());

      return false;
    } catch (UnsupportedOperationException UnsupportedOperationException) {
      return true;
    }
  }

  /**
   * Returns {@code true} if the {@link Map} throws an {@link UnsupportedOperationException} when calling
   * {@link Map#putAll} with an empty {@link Map#of}, false otherwise.
   *
   * @param map instance being tested for being unmodifiable
   * @return {@code true} if the {@link Map} throws an {@link UnsupportedOperationException} when calling
   *     {@link Map#putAll} with an empty {@link Map#of}, false otherwise
   */
  public static boolean isUnmodifiable(@NotNull Map<?, ?> map) {
    try {
      map.putAll(Map.of());

      return false;
    } catch (UnsupportedOperationException UnsupportedOperationException) {
      return true;
    }
  }

  /**
   * Returns an unmodifiable defensively copied list consisting of {@code ts}, filtering out any contained {@code null}
   * elements.
   *
   * @param ts  the source of instances of T, some elements of which may be {@code null} and will be filtered out
   * @param <T> the type of the instances in collection
   * @return an unmodifiable defensively copied list consisting of {@code ts}, filtering out any contained {@code null}
   *     elements
   */
  @NotNull
  public static <T> List<T> defensiveCopyToListUnmodifiableNonNulls(
      @Nullable Collection<T> ts
  ) {
    if ((ts != null) && !ts.isEmpty()) {
      return StreamsOps.toListUnmodifiableNonNulls(ts.stream());
    }

    return List.of();
  }

  /**
   * Returns an unmodifiable unordered defensively copied set consisting of {@code ts}, filtering out any contained
   * {@code null} elements.
   *
   * @param ts  the source of instances of T, some elements of which may be {@code null} and will be filtered out
   * @param <T> the type of the instances in collection
   * @return an unmodifiable unordered defensively copied set consisting of {@code ts}, filtering out any contained
   *     {@code null} elements
   */
  @NotNull
  public static <T> Set<T> defensiveCopyToSetUnmodifiableNonNulls(
      @Nullable Collection<T> ts
  ) {
    if ((ts != null) && !ts.isEmpty()) {
      return StreamsOps.toSetUnmodifiableNonNulls(ts.stream());
    }

    return Set.of();
  }

  /**
   * Returns an unmodifiable <i>ordered</i> defensively copied set consisting of {@code ts}, filtering out any contained
   * {@code null} elements.
   *
   * @param ts  the source of instances of T, some elements of which may be {@code null} and will be filtered out
   * @param <T> the type of the instances in collection
   * @return an unmodifiable <i>ordered</i> defensively copied set consisting of {@code ts}, filtering out any contained
   *     {@code null} elements
   */
  @NotNull
  public static <T> Set<T> defensiveCopyToSetOrderedUnmodifiableNonNulls(
      @Nullable Collection<T> ts
  ) {
    if ((ts != null) && !ts.isEmpty()) {
      return StreamsOps.toSetOrderedUnmodifiableNonNulls(ts.stream());
    }

    return Set.of();
  }

  /**
   * Returns an unmodifiable unordered defensively copied map consisting of {@code kAndVs}, filtering out any contained
   * {@code null} entries, within the key or the value.
   *
   * @param kAndVs the source of instances of entry, some elements of which may be {@code null} in either the key or the
   *               value, and will be filtered out
   * @param <K>    the type of the key instances within each collection entry
   * @param <V>    the type of the value instances within each collection entry
   * @return an unmodifiable unordered defensively copied map consisting of {@code kAndVs}, filtering out any contained
   *     {@code null} entries, within the key or the value
   */
  @NotNull
  public static <K, V> Map<K, V> defensiveCopyToMapUnmodifiableNonNulls(
      @Nullable Collection<Entry<K, V>> kAndVs
  ) {
    if ((kAndVs != null) && !kAndVs.isEmpty()) {
      return kAndVs
          .stream()
          .filter(entry ->
              (entry.getKey() != null) && (entry.getValue() != null))
          .collect(Collectors.toUnmodifiableMap(Entry::getKey, Entry::getValue));
    }

    return Map.of();
  }

  /**
   * Returns an unmodifiable <i>ordered</i> defensively copied map consisting of {@code kAndVs}, filtering out any
   * contained {@code null} entries, within the key or the value.
   *
   * @param kAndVs the source of instances of entry, some elements of which may be {@code null} in either the key or the
   *               value, and will be filtered out
   * @param <K>    the type of the key instances within each collection entry
   * @param <V>    the type of the value instances within each collection entry
   * @return an unmodifiable <i>ordered</i> defensively copied map consisting of {@code kAndVs}, filtering out any
   *     contained {@code null} entries, within the key or the value
   */
  @NotNull
  public static <K, V> Map<K, V> defensiveCopyToMapOrderedUnmodifiableNonNulls(
      @Nullable Collection<Entry<K, V>> kAndVs
  ) {
    if ((kAndVs != null) && !kAndVs.isEmpty()) {
      return StreamsOps.toMapOrderedUnmodifiable(kAndVs
          .stream()
          .filter(entry ->
              (entry.getKey() != null) && (entry.getValue() != null)));
    }

    return Map.of();
  }

  /**
   * Returns a new {@code int} array from a collection of {@link Integer}s.
   *
   * @param integers the source of the derived {@code int} values
   * @return a new {@code int} array from a collection of {@link Integer}s
   */
  public static int[] toDistinctSortedArrayInt(
      @NotNull Collection<Integer> integers
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
      @NotNull Collection<T> ts,
      @NotNull ToIntFunction<T> fTToId
  ) {
    return !ts.isEmpty()
        //@formatter:off
        ? ts
            .stream()
            .mapToInt(fTToId)
            .distinct()
            .sorted()
            .toArray()
        : ArraysOps.EMPTY_INT_ARRAY;
        //@formatter:on
  }
}
