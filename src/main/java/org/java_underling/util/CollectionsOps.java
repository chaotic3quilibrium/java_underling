package org.java_underling.util;

import org.java_underling.util.stream.StreamsOps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CollectionsOps {

  private CollectionsOps() {
    throw new UnsupportedOperationException("suppressing class instantiation");
  }

  /**
   * Returns an empty list using {@link List#of}, if {@code ts} is {@code null}, otherwise returns {@code ts}.
   *
   * @param ts  the list to reify to make null safe
   * @param <T> the type of instances contained in the {@link List}
   * @return an empty list using {@link List#of}, if {@code ts} is {@code null}, otherwise returns {@code ts}
   */
  @NotNull
  public static <T> List<T> nullToEmpty(@Nullable List<T> ts) {
    return ts != null
        ? ts
        : List.of();
  }

  /**
   * Returns an empty set using {@link Set#of}, if {@code ts} is {@code null}, otherwise returns {@code ts}.
   *
   * @param ts  the set to reify to make null safe
   * @param <T> the type of instances contained in the {@link Set}
   * @return an empty set using {@link Set#of}, if {@code ts} is {@code null}, otherwise returns {@code ts}
   */
  @NotNull
  public static <T> Set<T> nullToEmpty(@Nullable Set<T> ts) {
    return ts != null
        ? ts
        : Set.of();
  }

  /**
   * Returns an empty map using {@link Map#of}, if {@code mapKv} is {@code null}, otherwise returns {@code mapKv}.
   *
   * @param mapKv the map to reify to make null safe
   * @param <K>   the type of the key instances contained in the {@link Map}
   * @param <V>   the type of the value instances contained in the {@link Map}
   * @return an empty map using {@link Map#of}, if {@code mapKv} is {@code null}, otherwise returns {@code mapKv}
   */
  @NotNull
  public static <K, V> Map<K, V> nullToEmpty(@Nullable Map<K, V> mapKv) {
    return mapKv != null
        ? mapKv
        : Map.of();
  }

  /**
   * Returns {@code true} if the {@link Collection} throws an {@link UnsupportedOperationException} when calling
   * {@link Collection#addAll} with {@link Collections#emptyList}, false otherwise.
   *
   * @param collection instance being tested for being unmodifiable
   * @return {@code true} if the {@link Collection} throws an {@link UnsupportedOperationException} when calling
   * {@link Collection#addAll} with {@link Collections#emptyList}, false otherwise
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
   * {@link Map#putAll} with an empty {@link Map#of}, false otherwise
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
   * elements
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
   * {@code null} elements
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
   * {@code null} elements
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
   * {@code null} entries, within the key or the value
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
   * contained {@code null} entries, within the key or the value
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
   * Returns an unmodifiable list with the {@code value} appended.
   *
   * @param list  the source from which the copy is made
   * @param value the value to add to the copy of the list
   * @param <T>   the type of instances contained in the list
   * @return an unmodifiable list with the {@code value} appended
   */
  @NotNull
  public static <T> List<T> append(
      @NotNull List<T> list,
      @NotNull T value
  ) {
    if (!list.isEmpty()) {
      var result = new ArrayList<>(list);
      result.add(value);

      return Collections.unmodifiableList(result);
    }

    return List.of(value);
  }

  /**
   * Returns an unmodifiable unordered set with the {@code value} added.
   *
   * @param set   the source from which the unordered copy is made
   * @param value the value to add to the copy of the list
   * @param <T>   the type of instances contained in the set
   * @return an unmodifiable unordered set with the {@code value} added
   */
  @NotNull
  public static <T> Set<T> add(
      @NotNull Set<T> set,
      @NotNull T value
  ) {
    if (!set.isEmpty()) {
      var result = new HashSet<>(set);
      result.add(value);

      return Collections.unmodifiableSet(result);
    }

    return Set.of(value);
  }

  /**
   * Returns an unmodifiable <i>ordered</i> set with the {@code value} appended.
   *
   * @param set   the (assumed to be) <i>ordered</i> source from which the copy is made
   * @param value the value to add to the copy of the set
   * @param <T>   the type of instances contained in the set
   * @return an unmodifiable <i>ordered</i> set with the {@code value} appended
   */
  @NotNull
  public static <T> Set<T> append(
      @NotNull Set<T> set,
      @NotNull T value
  ) {
    if (!set.isEmpty()) {
      var result = new LinkedHashSet<>(set);
      result.add(value);

      return Collections.unmodifiableSet(result);
    }

    return Set.of(value);
  }

  /**
   * An unmodifiable list consisting of each list (filtered to non-null) from lists appended in iteration order.
   *
   * @param lists the lists to append
   * @param <T>   the type of instances contained within all the lists
   * @return an unmodifiable List consisting of each List (filtered to non-null) from lists appended in iteration order
   */
  @SuppressWarnings("ConstantValue")
  @NotNull
  @SafeVarargs
  public static <T> List<T> append(@NotNull List<T>... lists) {
    if (lists.length > 0) {
      var result = new ArrayList<T>();
      IntStream.range(0, lists.length)
          .forEach(index -> {
            var list = lists[index];
            if (list != null) {
              var listFilteredToNonNulls = list
                  .stream()
                  .filter(Objects::nonNull)
                  .toList();
              if (!listFilteredToNonNulls.isEmpty()) {
                result.addAll(listFilteredToNonNulls);
              }
            }
          });

      return !result.isEmpty()
          ? Collections.unmodifiableList(result)
          : List.of();
    }

    return List.of();
  }

  /**
   * An unmodifiable unordered set consisting of each set from sets added together
   *
   * @param sets the sets to append
   * @param <T>  the type of instances contained in all the sets
   * @return an unmodifiable unordered set consisting of each set from sets added together
   */
  @NotNull
  @SafeVarargs
  public static <T> Set<T> add(
      @NotNull Set<T>... sets
  ) {
    return add(false, sets);
  }

  /**
   * An unmodifiable unordered set consisting of each set from sets added together
   *
   * @param isRetainingNulls if true, allows elements with a {@code null} value
   * @param sets             the sets to append
   * @param <T>              the type of instances contained in all the sets
   * @return an unmodifiable unordered set consisting of each set from sets added together
   */
  @SuppressWarnings("ConstantValue")
  @NotNull
  @SafeVarargs
  public static <T> Set<T> add(
      boolean isRetainingNulls,
      @NotNull Set<T>... sets
  ) {
    if (sets.length > 0) {
      var result = new HashSet<T>();
      IntStream.range(0, sets.length)
          .forEach(index -> {
            var set = sets[index];
            if (set != null) {
              var resolvedSet = !isRetainingNulls
                  //@formatter:off
                  ? set
                      .stream()
                      .filter(Objects::nonNull)
                      .collect(Collectors.toUnmodifiableSet())
                  : set;
                  //@formatter:on
              if (!resolvedSet.isEmpty()) {
                result.addAll(resolvedSet);
              }
            }
          });

      return !result.isEmpty()
          ? Collections.unmodifiableSet(result)
          : Set.of();
    }

    return Set.of();
  }

  /**
   * Return an unmodifiable <i>ordered</i> set consisting of each set from sets added together
   *
   * @param sets the (assumed to be) <i>ordered</i> sets to append
   * @param <T>  the type of instances contained in all the sets
   * @return an unmodifiable <i>ordered</i> set consisting of each set from sets added together
   */
  @NotNull
  @SafeVarargs
  public static <T> Set<T> append(
      @NotNull Set<T>... sets
  ) {
    return add(false, sets);
  }

  /**
   * Return an unmodifiable <i>ordered</i> set consisting of each set from sets added together
   *
   * @param isRetainingNulls if true, allows elements with a {@code null} value
   * @param sets             the (assumed to be) <i>ordered</i> sets to append
   * @param <T>              the type of instances contained in all the sets
   * @return an unmodifiable <i>ordered</i> set consisting of each set from sets added together
   */
  @SuppressWarnings("ConstantValue")
  @NotNull
  @SafeVarargs
  public static <T> Set<T> append(
      boolean isRetainingNulls,
      @NotNull Set<T>... sets
  ) {
    if (sets.length > 0) {
      var result = new LinkedHashSet<T>();
      IntStream.range(0, sets.length)
          .forEach(index -> {
            var set = sets[index];
            if (set != null) {
              var resolvedSet = !isRetainingNulls
                  ? StreamsOps.toSetOrderedUnmodifiableNonNulls(set.stream())
                  : set;
              if (!resolvedSet.isEmpty()) {
                result.addAll(resolvedSet);
              }
            }
          });

      return !result.isEmpty()
          ? Collections.unmodifiableSet(result)
          : Set.of();
    }

    return Set.of();
  }

  /**
   * Returns an unmodifiable unordered map where the keys and values have been swapped.
   * <p>
   * ---
   * <p>
   * Swaps a Map where, for each entry, the value becomes the key, and the key becomes the value. In the event the
   * values in the supplied Map are not unique, i.e. there is more than one key with the same value (according to
   * {@link Object#equals}), it is undefined which unique key is retained as a value, and which other keys are silently
   * dropped.
   *
   * @param map the map in which every entry will swap the key and value
   * @param <K> the type of the key instances
   * @param <V> the type of the value instances
   * @return an unmodifiable unordered map where the keys and values have been swapped
   */
  @NotNull
  public static <K, V> Map<V, K> swap(
      @NotNull Map<K, V> map
  ) {
    return swap(map, false, false);
  }

  /**
   * Returns an unmodifiable unordered map where the keys and values have been swapped.
   * <p>
   * ---
   * <p>
   * Swaps a Map where, for each entry, the value becomes the key, and the key becomes the value. In the event the
   * values in the supplied Map are not unique, i.e. there is more than one key with the same value (according to
   * {@link Object#equals}), it is undefined which unique key is retained as a value, and which other keys are silently
   * dropped.
   *
   * @param map                   the map in which every entry will swap the key and value
   * @param isRetainingNullsKey   when true, allows the use of a {@code null} key, otherwise filters out the
   *                              {@link Map.Entry}
   * @param isRetainingNullsValue when true, allows the use of a {@code null} value, otherwise filters out the
   *                              {@link Map.Entry}
   * @param <K>                   the type of the key instances
   * @param <V>                   the type of the value instances
   * @return an unmodifiable unordered map where the keys and values have been swapped
   */
  @NotNull
  public static <K, V> Map<V, K> swap(
      @NotNull Map<K, V> map,
      boolean isRetainingNullsKey,
      boolean isRetainingNullsValue
  ) {
    return !map.isEmpty()
        //@formatter:off
        ? Collections.unmodifiableMap(map
            .entrySet()
            .stream()
            .filter(entry ->
                (isRetainingNullsKey || (entry.getKey() != null)) &&
                    (isRetainingNullsValue || (entry.getValue() != null)))
            .collect(Collectors.toMap(Entry::getValue, Entry::getKey)))
        : Map.of();
        //@formatter:on
  }

  /**
   * Returns an unmodifiable <i>ordered</i> map where the keys and values have been swapped.
   * <p>
   * ---
   * <p>
   * Swaps a Map where, for each entry, the value becomes the key, and the key becomes the value. In the event the
   * values in the supplied Map are not unique, i.e. there is more than one key with the same value (according to
   * {@link Object#equals}), it is undefined which unique key is retained as a value, and which other keys are silently
   * dropped.
   *
   * @param map the map in which every entry will swap the key and value
   * @param <K> the type of the key instances
   * @param <V> the type of the value instances
   * @return an unmodifiable unordered map where the keys and values have been swapped
   */
  @NotNull
  public static <K, V> Map<V, K> swapOrdered(
      @NotNull Map<K, V> map
  ) {
    return swapOrdered(map, false, false);
  }

  /**
   * Returns an unmodifiable <i>ordered</i> map where the keys and values have been swapped.
   * <p>
   * ---
   * <p>
   * Swaps a Map where, for each entry, the value becomes the key, and the key becomes the value. In the event the
   * values in the supplied Map are not unique, i.e. there is more than one key with the same value (according to
   * {@link Object#equals}), it is undefined which unique key is retained as a value, and which other keys are silently
   * dropped.
   *
   * @param map                   the map in which every entry will swap the key and value
   * @param isRetainingNullsKey   when true, allows the use of a {@code null} key, otherwise filters out the
   *                              {@link Map.Entry}
   * @param isRetainingNullsValue when true, allows the use of a {@code null} value, otherwise filters out the
   *                              {@link Map.Entry}
   * @param <K>                   the type of the key instances
   * @param <V>                   the type of the value instances
   * @return an unmodifiable unordered map where the keys and values have been swapped
   */
  @NotNull
  public static <K, V> Map<V, K> swapOrdered(
      @NotNull Map<K, V> map,
      boolean isRetainingNullsKey,
      boolean isRetainingNullsValue
  ) {
    if (!map.isEmpty()) {
      var result = new LinkedHashMap<V, K>();
      //noinspection SimplifyStreamApiCallChains
      map.keySet()
          .stream()
          .forEachOrdered(
              k -> {
                if (isRetainingNullsKey || (k != null)) {
                  var v = map.get(k);
                  if (isRetainingNullsValue || (v != null)) {
                    result.put(v, k);
                  }
                }
              });

      return Collections.unmodifiableMap(result);
    }

    return Map.of();
  }
}
