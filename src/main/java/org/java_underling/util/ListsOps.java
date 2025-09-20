package org.java_underling.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

//TODO: fill out javadoc
public class ListsOps {

  private ListsOps() {
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
   * Return a new {@link List} from a collection of {@code integers}.
   *
   * @param integers the source of the derived {@link Integer} values
   * @return a new {@link List} from a collection of {@code integers}
   */
  @NotNull
  public static List<Integer> toDistinctSortedListInteger(
      @NotNull Collection<Integer> integers
  ) {
    return toDistinctSortedListInteger(integers, Function.identity());
  }

  /**
   * Return a new {@link List} from a collection of {@code ts} deriving the {@link Integer} value via the function
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
      @NotNull Collection<T> ts,
      @NotNull Function<T, Integer> fTToId
  ) {
    return ts
        .stream()
        .map(fTToId)
        .distinct()
        .sorted()
        .toList();
  }
}
