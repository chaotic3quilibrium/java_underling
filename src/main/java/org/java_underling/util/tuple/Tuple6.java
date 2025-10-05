package org.java_underling.util.tuple;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

//TODO: x2 missing javadocs
public record Tuple6<T1, T2, T3, T4, T5, T6>(
    @NotNull T1 _1,
    @NotNull T2 _2,
    @NotNull T3 _3,
    @NotNull T4 _4,
    @NotNull T5 _5,
    @NotNull T6 _6
) implements Tuple {
  @Override
  public int arity() {
    return 6;
  }

  @Override
  public Stream<?> stream() {
    return Stream.of(
        _1, _2, _3, _4, _5, _6);
  }

  //TODO: x13 add "withers"
  // - map() defining functions for each field
  // - map_N() defining a single function to type transform a specific single field
  // - update_N() defining a single function to update the value of a specific single field
}
