package org.java_underling.util.tuple;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public record Tuple4<T1, T2, T3, T4>(
    @NotNull T1 _1,
    @NotNull T2 _2,
    @NotNull T3 _3,
    @NotNull T4 _4
) implements Tuple {
  @Override
  public int arity() {
    return 4;
  }

  @Override
  public Stream<?> stream() {
    return Stream.of(
        _1, _2, _3, _4);
  }
}
