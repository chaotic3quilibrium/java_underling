package org.java_underling.util.stream;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Map.entry;

/**
 * Utility class providing static methods to create {@link Stream} instances from various types of <i>iterable</i>
 * sources.
 * <p>
 * And to facilitate easier creation of unmodifiable non-{@code null} {@link List}s, and unmodifiable <i>ordered</i>
 * non-{@code null} {@link Set}s and {@link Map}s.
 */
public final class StreamsOps {

  private StreamsOps() {
    throw new UnsupportedOperationException("suppressing class instantiation");
  }

  /**
   * Creates a sequential {@link Stream} from a given {@link Iterator}.
   *
   * @param iterator The {@code Iterator} to create the stream from. Must not be {@code null}.
   * @param <T>      The type of elements in the stream.
   * @return A new sequential {@code Stream} containing the elements from the iterator.
   * @throws NullPointerException if the provided {@code iterator} is {@code null}.
   */
  @NotNull
  public static <T> Stream<T> from(
      @NotNull Iterator<T> iterator
  ) {
    return from(iterator, false);
  }

  /**
   * Creates a {@link Stream} from a given {@link Iterator}, allowing for parallel processing.
   *
   * @param iterator   The {@code Iterator} to create the stream from. Must not be {@code null}.
   * @param isParallel If {@code true}, the resulting stream will be parallel; otherwise, it will be sequential.
   * @param <T>        The type of elements in the stream.
   * @return A new {@code Stream} containing the elements from the iterator, with the specified parallelism.
   * @throws NullPointerException if the provided {@code iterator} is {@code null}.
   */
  @NotNull
  public static <T> Stream<T> from(
      @NotNull Iterator<T> iterator,
      boolean isParallel
  ) {
    Iterable<T> iterable = () -> iterator;

    return from(iterable, isParallel);
  }

  /**
   * Creates a sequential {@link Stream} from a given {@link Iterable}.
   *
   * @param iterable The {@code Iterable} to create the stream from. Must not be {@code null}.
   * @param <T>      The type of elements in the stream.
   * @return A new sequential {@code Stream} containing the elements from the iterable.
   * @throws NullPointerException if the provided {@code iterable} is {@code null}.
   */
  @NotNull
  public static <T> Stream<T> from(
      @NotNull Iterable<T> iterable
  ) {
    return from(iterable, false);
  }

  /**
   * Creates a {@link Stream} from a given {@link Iterable}, allowing for parallel processing.
   *
   * @param iterable   The {@code Iterable} to create the stream from. Must not be {@code null}.
   * @param isParallel If {@code true}, the resulting stream will be parallel; otherwise, it will be sequential.
   * @param <T>        The type of elements in the stream.
   * @return A new {@code Stream} containing the elements from the iterable, with the specified parallelism.
   * @throws NullPointerException if the provided {@code iterable} is {@code null}.
   */
  @NotNull
  public static <T> Stream<T> from(
      @NotNull Iterable<T> iterable,
      boolean isParallel
  ) {
    return StreamSupport.stream(iterable.spliterator(), isParallel);
  }

  /**
   * Returns a new lazy Stream of Entry where each entry is composed of the next element at the same index in both
   * streams, terminating with the shorter of the two Streams.
   *
   * @param streamLs the source of the left side (key) elements
   * @param streamRs the source of the right side (value) elements
   * @param <L>      the type of the left elements in the stream
   * @param <R>      the type of the right elements in the stream
   * @return a new lazy Stream of Entry where each entry is composed of the next element at the same index in both
   *     streams, terminating with the shorter of the two Streams
   */
  @NotNull
  public static <L, R> Stream<Entry<L, R>> zip(
      @NotNull Stream<L> streamLs,
      @NotNull Stream<R> streamRs
  ) {
    Iterator<R> iteratorRs = streamRs.iterator();

    return streamLs
        .filter(l ->
            iteratorRs.hasNext())
        .map(l ->
            entry(l, iteratorRs.next()));
  }

  /**
   * Return a new lazy Stream of Entry where each entry is composed of the next element, and its associated zero-based
   * index.
   *
   * @param streamTs the source of the elements (keys) with which to associate an zero based index
   * @param <T>      the type of the elements in the stream
   * @return a new lazy Stream of Entry where each entry is composed of the next element, and its associated zero-based
   *     index
   */
  @NotNull
  public static <T> Stream<Entry<T, Integer>> zipWithIndex(
      @NotNull Stream<T> streamTs
  ) {
    var atomicInteger = new AtomicInteger(0);

    return streamTs
        .map(t ->
            entry(t, atomicInteger.getAndIncrement()));
  }

  /**
   * Returns an unmodifiable list with the null elements filtered out.
   *
   * @param stream the source of the T elements
   * @param <T>    the type of the instances
   * @return an unmodifiable list with the null elements filtered out
   */
  @NotNull
  public static <T> List<T> toListUnmodifiableNonNulls(
      @NotNull Stream<T> stream
  ) {
    return stream
        .filter(t ->
            !Objects.isNull(t))
        .toList();
  }

  /**
   * Returns an unmodifiable set with the null elements filtered out.
   *
   * @param stream the source of the T elements
   * @param <T>    the type of the instances
   * @return an unmodifiable set with the null elements filtered out
   */
  @NotNull
  public static <T> Set<T> toSetUnmodifiableNonNulls(
      @NotNull Stream<T> stream
  ) {
    return stream
        .filter(t ->
            !Objects.isNull(t))
        .collect(Collectors.toUnmodifiableSet());
  }

  /**
   * Returns an unmodifiable <i>ordered</i> set with the null elements filtered out.
   *
   * @param stream the source of the T elements
   * @param <T>    the type of the instances
   * @return an unmodifiable <i>ordered</i> set with the null elements filtered out
   */
  @NotNull
  public static <T> Set<T> toSetOrderedUnmodifiableNonNulls(
      @NotNull Stream<T> stream
  ) {
    return toSetOrderedUnmodifiable(stream.filter(t ->
        !Objects.isNull(t)));
  }

  /**
   * Returns an unmodifiable <i>ordered</i> set, which may contain {@code null} values.
   *
   * @param stream the source of the T elements
   * @param <T>    the type of the instances
   * @return an unmodifiable <i>ordered</i> set, which may contain {@code null} values
   */
  @NotNull
  public static <T> Set<T> toSetOrderedUnmodifiable(
      @NotNull Stream<T> stream
  ) {
    var set = stream.collect(Collectors.toCollection(LinkedHashSet::new));

    return !set.isEmpty()
        ? Collections.unmodifiableSet(set)
        : Set.of();
  }

  /**
   * Returns an unmodifiable <i>ordered</i> map with the null entries, key or value, filtered out.
   *
   * @param kAndVs the source of the entries
   * @param <K>    the type of the key in the entries
   * @param <V>    the type of the value in the entries
   * @return an unmodifiable <i>ordered</i> map with the null entries, key or value, filtered out
   */
  @NotNull
  public static <K, V> Map<K, V> toMapOrderedUnmodifiableNonNulls(
      @NotNull Stream<Entry<K, V>> kAndVs
  ) {
    return toMapOrderedUnmodifiable(kAndVs.filter(t ->
        !Objects.isNull(t) && (!Objects.isNull(t.getKey()) && !Objects.isNull(t.getValue()))));
  }

  /**
   * Returns an unmodifiable <i>ordered</i> map, which may contain {@code null} within each entry for the key and/or
   * value.
   *
   * @param kAndVs the source of the entries
   * @param <K>    the type of the key in the entries
   * @param <V>    the type of the value in the entries
   * @return an unmodifiable <i>ordered</i> map, which may contain {@code null} within each entry for the key and/or
   *     value
   */
  @NotNull
  public static <K, V> Map<K, V> toMapOrderedUnmodifiable(
      @NotNull Stream<Entry<K, V>> kAndVs
  ) {
    return toMapOrderedUnmodifiable(kAndVs, Optional::of);
  }

  /**
   * Returns an unmodifiable <i>ordered</i> map with the null entries, key and/or value, filtered out, and where any
   * duplicate key discards the entry.
   *
   * @param ts  the source of the input to create entries
   * @param <T> the type of the source value the entries
   * @param <K> the type of the key in the entries
   * @param <V> the type of the value in the entries
   * @return an unmodifiable <i>ordered</i> map with the null entries, key or value, filtered out, and where any
   *     duplicate key discards the entry
   */
  @NotNull
  public static <T, K, V> Map<K, V> toMapOrderedUnmodifiableNonNulls(
      @NotNull Stream<T> ts,
      @NotNull Function<T, Optional<Entry<K, V>>> fTtoOptionalEntry
  ) {
    return toMapOrderedUnmodifiable(
        ts.filter(t ->
            !Objects.isNull(t)),
        fTtoOptionalEntry.andThen(optionalEntry ->
            optionalEntry.filter(entry ->
                (entry.getKey() != null) && (entry.getValue() != null))));
  }

  /**
   * Returns an unmodifiable <i>ordered</i> map, which may contain {@code null} within each entry for the key and/or
   * value, and where any duplicate key discards the entry.
   *
   * @param ts  the source of the input to create the entries
   * @param <T> the type of the input value to create the entries
   * @param <K> the type of the key in the created entry
   * @param <V> the type of the value in the created entry
   * @return an unmodifiable <i>ordered</i> map, which may contain {@code null} within each entry for the key and/or
   *     value, and where any duplicate key discards the entry
   */
  @NotNull
  public static <T, K, V> Map<K, V> toMapOrderedUnmodifiable(
      @NotNull Stream<T> ts,
      @NotNull Function<T, Optional<Entry<K, V>>> fTtoOptionalEntry
  ) {
    return Collections.unmodifiableMap(ts
        .flatMap(t ->
            fTtoOptionalEntry.apply(t).stream())
        .collect(Collectors.toMap(
            Entry::getKey,
            Entry::getValue,
            (vOld, vNew) ->
                vOld,
            LinkedHashMap::new)));
  }
}
