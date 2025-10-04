package org.java_underling.util.tuple;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public record Tuple1<T1>(
    @NotNull T1 _1
) implements Tuple {
  @Override
  public int arity() {
    return 1;
  }

  @Override
  public Stream<?> stream() {
    return Stream.of(
        _1);
  }
}

