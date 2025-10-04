package org.java_underling.util.tuple;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public record Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(
    @NotNull T1 _1,
    @NotNull T2 _2,
    @NotNull T3 _3,
    @NotNull T4 _4,
    @NotNull T5 _5,
    @NotNull T6 _6,
    @NotNull T7 _7,
    @NotNull T8 _8,
    @NotNull T9 _9
) implements Tuple {
  @Override
  public int arity() {
    return 9;
  }

  @Override
  public Stream<?> stream() {
    return Stream.of(
        _1, _2, _3, _4, _5, _6, _7, _8, _9);
  }
}
