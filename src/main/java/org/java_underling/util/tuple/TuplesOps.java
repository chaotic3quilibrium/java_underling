package org.java_underling.util.tuple;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import java.util.Objects;

import static java.util.Map.entry;

public class TuplesOps {
  private TuplesOps() {
    throw new UnsupportedOperationException("suppressing class instantiation");
  }

  @NotNull
  public static <A, B> Tuple2<A, B> from(
      @NotNull Entry<A, B> entry
  ) {
    return new Tuple2<>(
        Objects.requireNonNull(entry.getKey()),
        Objects.requireNonNull(entry.getValue()));
  }

  @NotNull
  public static <A, B> Entry<A, B> to(
      @NotNull Tuple2<A, B> tuple
  ) {
    return entry(
        tuple._1(),
        tuple._2());
  }

  @NotNull
  public static <A, B> Tuple2<A, B> from(
      @NotNull SimpleImmutableEntry<A, B> simpleImmutableEntry
  ) {
    return new Tuple2<>(
        Objects.requireNonNull(simpleImmutableEntry.getKey()),
        Objects.requireNonNull(simpleImmutableEntry.getValue()));
  }

  @NotNull
  public static <A, B> SimpleImmutableEntry<A, B> toSimpleImmutableEntry(
      @NotNull Tuple2<A, B> tuple
  ) {
    return new SimpleImmutableEntry<>(
        tuple._1(),
        tuple._2());
  }
}
