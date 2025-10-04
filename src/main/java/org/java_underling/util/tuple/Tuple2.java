package org.java_underling.util.tuple;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

//public record Tuple2<T1, T2>(
public record Tuple2<T1, T2>(
    @NotNull T1 _1,
    @NotNull T2 _2
) implements Tuple {
  @Override
  public int arity() {
    return 2;
  }

  @Override
  public Stream<?> stream() {
    return Stream.of(
        _1, _2);
  }
}
