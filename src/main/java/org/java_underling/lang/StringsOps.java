package org.java_underling.lang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class StringsOps {

  private StringsOps() {
    throw new UnsupportedOperationException("suppressing class instantiation");
  }

  @NotNull
  public static String nullToEmpty(@Nullable String string) {
    return string != null
        ? string
        : "";
  }

  public static boolean equalsIgnoreCaseNullable(
      @Nullable String stringA,
      @Nullable String stringB
  ) {
    var isStringANull = Objects.isNull(stringA);

    return (isStringANull == Objects.isNull(stringB)) &&
        (isStringANull || stringA.equalsIgnoreCase(stringB));
  }
}
