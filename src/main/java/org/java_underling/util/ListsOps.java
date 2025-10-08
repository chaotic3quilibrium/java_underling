package org.java_underling.util;

import org.java_underling.util.refined.NonEmptyList;
import org.java_underling.util.tuple.Tuple2;
import org.java_underling.util.tuple.Tuple3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Utility class providing static methods to create {@link List} instances.
 */
public class ListsOps {

  private ListsOps() {
    throw new UnsupportedOperationException("suppressing class instantiation");
  }

  /**
   * Returns an empty {@link List} using {@link List#of}, if {@code ts} is {@code null}, otherwise returns {@code ts}.
   * <p>
   * Note: If the requirement is to both avoid a {@code null} value, and an empty {@link List}, the refined class of
   * {@link NonEmptyList} enable <i>compile-time enforcement</i> of said contract requirements.
   *
   * @param ts  possibly {@code null} {@link List} to reify to make {@code null} safe
   * @param <T> the type of instances contained in the {@link List}
   * @return an empty {@link List} using {@link List#of}, if {@code ts} is {@code null}, otherwise returns {@code ts}
   */
  @NotNull
  public static <T> List<T> nullToEmpty(@Nullable List<T> ts) {
    return ts != null
        ? ts
        : List.of();
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
  public static <T> List<T> appendItem(
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
   * Returns an unmodifiable list consisting of each list (filtered to non-null) from lists appended in iteration
   * order.
   *
   * @param lists the lists to append
   * @param <T>   the type of instances contained within all the lists
   * @return an unmodifiable List consisting of each List (filtered to non-null) from lists appended in iteration order
   */
  @SuppressWarnings("ConstantValue")
  @NotNull
  @SafeVarargs
  public static <T> List<T> appendLists(@NotNull List<T>... lists) {
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
   * Returns an unmodifiable list with the null elements filtered out.
   *
   * @param stream the source of the T elements
   * @param <T>    the type of the instances
   * @return an unmodifiable list with the null elements filtered out
   */
  @NotNull
  public static <T> List<T> toListUnmodifiable(
      @NotNull Stream<T> stream
  ) {
    return stream
        .filter(t ->
            !Objects.isNull(t))
        .toList();
  }

  /**
   * Returns a new {@link List} from a collection of {@code integers}.
   *
   * @param integers the source of the derived {@link Integer} values
   * @return a new {@link List} from a collection of {@code integers}
   */
  @NotNull
  public static List<Integer> toDistinctSortedListInteger(
      @NotNull Stream<Integer> integers
  ) {
    return toDistinctSortedListInteger(integers, Function.identity());
  }

  /**
   * Returns a new {@link List} from a collection of {@code ts} deriving the {@link Integer} value via the function
   * {@code fTToId}.
   *
   * @param ts     the source of the derived {@link Integer} values
   * @param fTToId the function to use to extract the {@link Integer} value from an element of the collection
   * @param <T>    the type of instances contained in the collection
   * @return a new {@link List} from a collection of {@code ts} deriving the {@link Integer} value via the function
   *     {@code fTToId}
   */
  @NotNull
  public static <T> List<Integer> toDistinctSortedListInteger(
      @NotNull Stream<T> ts,
      @NotNull Function<T, Integer> fTToId
  ) {
    return ts
        .map(fTToId)
        .distinct()
        .sorted()
        .toList();
  }

  /**
   * Returns a new {@link List} from a {@link Stream} of {@link Optional}  elements, extracting the non-empty
   * {@link Optional} elements, and filtering out the {@link Optional} empty elements.
   *
   * @param optionalTs the list from which to extract the non-empty optional values
   * @param <T>        the type of instances contained in the collection
   * @return a new {@link List} from a {@link Stream} of {@link Optional}  elements, extracting the non-empty
   *     {@link Optional} elements, and filtering out the {@link Optional} empty elements
   */
  @NotNull
  public static <T> List<T> flatten(
      @NotNull Stream<Optional<T>> optionalTs
  ) {
    return optionalTs
        .flatMap(Optional::stream)
        .toList();
  }

  /**
   * Returns {@link List}s in a {@link Tuple2} extracted from a {@link Stream} of {@link Tuple2}s.
   *
   * @param aAndBs the list of tuples from which to extract the lists
   * @param <A>    the type of instances contained within the first element of each tuple
   * @param <B>    the type of instances contained within the second element of each tuple
   * @return {@link List}s in a {@link Tuple2} extracted from a {@link Stream} of {@link Tuple2}s
   */
  @NotNull
  public static <A, B> Tuple2<List<A>, List<B>> unzip(
      @NotNull Stream<Tuple2<A, B>> aAndBs
  ) {
    var listA = new ArrayList<A>();
    var listB = new ArrayList<B>();
    aAndBs.forEachOrdered(lAndR -> {
      listA.add(lAndR._1());
      listB.add(lAndR._2());
    });
    if (!listA.isEmpty()) {

      return new Tuple2<>(
          Collections.unmodifiableList(listA),
          Collections.unmodifiableList(listB));
    }

    return new Tuple2<>(
        List.of(),
        List.of());
  }

  /**
   * Returns {@link List}s in a {@link Tuple3} extracted from a {@link Stream} of {@link Tuple3}s.
   *
   * @param aAndBAndCs the list of tuples from which to extract the lists
   * @param <A>        the type of instances contained within the first element of each tuple
   * @param <B>        the type of instances contained within the second element of each tuple
   * @param <C>        the type of instances contained within the third element of each tuple
   * @return {@link List}s in a {@link Tuple3} extracted from a {@link Stream} of {@link Tuple3}s
   */
  @NotNull
  public static <A, B, C> Tuple3<List<A>, List<B>, List<C>> unzip3(
      @NotNull Stream<Tuple3<A, B, C>> aAndBAndCs
  ) {
    var listA = new ArrayList<A>();
    var listB = new ArrayList<B>();
    var listC = new ArrayList<C>();
    aAndBAndCs.forEachOrdered(aAndBAndC -> {
      listA.add(aAndBAndC._1());
      listB.add(aAndBAndC._2());
      listC.add(aAndBAndC._3());
    });
    if (!listA.isEmpty()) {

      return new Tuple3<>(
          Collections.unmodifiableList(listA),
          Collections.unmodifiableList(listB),
          Collections.unmodifiableList(listC));
    }

    return new Tuple3<>(
        List.of(),
        List.of(),
        List.of());
  }

  /**
   * Return {@link List}s in a {@link Tuple2} extracted from a {@link Stream} of {@link Tuple2}s filtered by a the
   * {@code fAAndBToToOptionalOptionalAAndOptionalB} function.
   *
   * @param aAndBs                                  the list of tuples from which to extract the lists
   * @param fAAndBToToOptionalOptionalAAndOptionalB the function to use to extract the values from an element of the
   *                                                collection and optionally return a value, and when non-empty,
   *                                                optionally return each of the element values
   * @param <A>                                     the type of instances contained within the first element of each
   *                                                tuple
   * @param <B>                                     the type of instances contained within the second element of each
   *                                                tuple
   * @return {@link List}s in a {@link Tuple2} extracted from a {@link Stream} of {@link Tuple2}s filtered by a the
   *     {@code fAAndBToToOptionalOptionalAAndOptionalB} function.
   */
  @NotNull
  public static <A, B> Tuple2<List<A>, List<B>> unzipAndFlatten(
      @NotNull Stream<Tuple2<A, B>> aAndBs,
      @NotNull Function<Tuple2<A, B>, Optional<Tuple2<Optional<A>, Optional<B>>>> fAAndBToToOptionalOptionalAAndOptionalB
  ) {
    var listA = new ArrayList<A>();
    var listB = new ArrayList<B>();
    aAndBs.forEachOrdered(aAndB -> {
      var optionalOptionalAAndOptionalB = fAAndBToToOptionalOptionalAAndOptionalB.apply(aAndB);
      optionalOptionalAAndOptionalB
          .ifPresent(optionalAAndOptionalB -> {
            optionalAAndOptionalB._1()
                .ifPresent(listA::add);
            optionalAAndOptionalB._2()
                .ifPresent(listB::add);
          });
    });
    if (!listA.isEmpty() || !listB.isEmpty()) {
      return new Tuple2<>(
          Collections.unmodifiableList(listA),
          Collections.unmodifiableList(listB));
    }

    return new Tuple2<>(
        List.of(),
        List.of());
  }

  /**
   * Returns {@link List}s in a {@link Tuple2} extracted from a {@link Stream} of {@link Either}s.
   *
   * @param eithers the list from which to extract the lists
   * @param <L>     the type of instances contained within the left element of each either
   * @param <R>     the type of instances contained within the right element of each either
   * @return {@link List}s in a {@link Tuple2} extracted from a {@link Stream} of {@link Either}s
   */
  @NotNull
  public static <L, R> Tuple2<List<Optional<L>>, List<Optional<R>>> unzipEithers(
      @NotNull Stream<Either<L, R>> eithers
  ) {
    return unzip(eithers
        .map(
            either ->
                new Tuple2<>(
                    either.toOptionalLeft(),
                    either.toOptionalRight())));
  }

  /**
   * Returns {@link List}s in a {@link Tuple2} extracted from a {@link Stream} of {@link Either}s, {@link #flatten}ing
   * each returned list.
   *
   * @param eithers the list from which to extract the lists
   * @param <L>     the type of instances contained within the left element of each either
   * @param <R>     the type of instances contained within the right element of each either
   * @return {@link List}s in a {@link Tuple2} extracted from a {@link Stream} of {@link Either}s, {@link #flatten}ing
   *     each returned list
   */
  @NotNull
  public static <L, R> Tuple2<List<L>, List<R>> unzipAndFlattenEithers(
      @NotNull Stream<Either<L, R>> eithers
  ) {
    var unzippedEithers = unzipEithers(eithers);

    return new Tuple2<>(
        flatten(unzippedEithers._1().stream()),
        flatten(unzippedEithers._2().stream()));
  }
}
