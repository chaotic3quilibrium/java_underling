package org.java_underling.util.tuple;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public record Tuple3<T1, T2, T3>(
    @NotNull T1 _1,
    @NotNull T2 _2,
    @NotNull T3 _3
) implements Tuple {
  @Override
  public int arity() {
    return 3;
  }

  @Override
  public Stream<?> stream() {
    return Stream.of(
        _1, _2, _3);
  }
}
