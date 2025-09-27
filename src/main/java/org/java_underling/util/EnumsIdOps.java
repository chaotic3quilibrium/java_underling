package org.java_underling.util;


import org.java_underling.lang.ClassesOps;
import org.java_underling.util.stream.StreamsOps;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

public final class EnumsIdOps<E extends Enum<E>, ID> {
  private static final Object ENUM_ID_OPS_BY_ENUM_CLASS_SYNC = new Object();
  private static final Object EXTENDED_CONTEXT_BY_ENUM_CLASS_LOCK = new Object();
  private static final Map<Class<?>, ExtendedContext<?, ?>> EXTENDED_CONTEXT_BY_ENUM_CLASS = new HashMap<>();
  private static volatile Memoizer<Class<?>, EnumsIdOps<?, ?>> ENUM_ID_OPS_BY_ENUM_CLASS;

  @NotNull
  public static <E extends Enum<E>> EnumsIdOps<E, Integer> from(
      @NotNull Class<E> enumClassE
  ) {
    return from(enumClassE, 0);
  }

  @NotNull
  public static <E extends Enum<E>> EnumsIdOps<E, Integer> from(
      @NotNull Class<E> enumClassE,
      int ordinalOffset
  ) {
    return from(
        enumClassE,
        enumValue ->
            enumValue.ordinal() + ordinalOffset,
        Object::toString,
        Optional.empty());
  }

  public static <E extends Enum<E>, ID> EnumsIdOps<E, ID> from(
      @NotNull Class<E> enumClassE,
      @NotNull Function<E, ID> fEToId
  ) {
    return from(
        enumClassE,
        fEToId,
        Object::toString,
        Optional.empty());
  }

  private record ExtendedContext<E extends Enum<E>, ID>(
      @NotNull Function<E, ID> fEToId,
      @NotNull Function<ID, String> fIdToString,
      @NotNull Optional<Function<E, String>> optionalFEToAltString
  ) {

  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  @NotNull
  public static <E extends Enum<E>, ID> EnumsIdOps<E, ID> from(
      @NotNull Class<E> classE,
      @NotNull Function<E, ID> fEToId,
      @NotNull Function<ID, String> fIdToString,
      @NotNull Optional<Function<E, String>> optionalFEToAltString
  ) {
    if (ENUM_ID_OPS_BY_ENUM_CLASS == null) {
      synchronized (ENUM_ID_OPS_BY_ENUM_CLASS_SYNC) {
        if (ENUM_ID_OPS_BY_ENUM_CLASS == null) {
          //noinspection unchecked
          ENUM_ID_OPS_BY_ENUM_CLASS = Memoizer.from(classWildcard ->
              ClassesOps.narrow(() ->
                      (Class<E>) classWildcard)
                  .map(classEnumE ->
                      Optional.ofNullable(EXTENDED_CONTEXT_BY_ENUM_CLASS.get(classEnumE))
                          .map(extendedContextWildcardWildcard ->
                              ClassesOps.narrow(() ->
                                      (ExtendedContext<E, ID>) extendedContextWildcardWildcard)
                                  .map(extendedContextNarrowed ->
                                      new EnumsIdOps<>(
                                          classEnumE,
                                          extendedContextNarrowed.fEToId,
                                          extendedContextNarrowed.fIdToString,
                                          extendedContextNarrowed.optionalFEToAltString))
                                  .orElseThrow(() ->
                                      new IllegalStateException("unable to narrow extended context for class " + classEnumE.getName())))
                          .orElseThrow(() ->
                              new IllegalStateException("unable to obtain extended context for class " + classEnumE.getName())))
                  .orElseThrow(() ->
                      new IllegalStateException("unable to narrow classWildcard for class " + classE.getName())));
        }
      }
    }

    synchronized (EXTENDED_CONTEXT_BY_ENUM_CLASS_LOCK) {
      EXTENDED_CONTEXT_BY_ENUM_CLASS.put(classE, new ExtendedContext<>(fEToId, fIdToString, optionalFEToAltString));
      try {
        //noinspection unchecked
        return ClassesOps.narrow(() ->
                (EnumsIdOps<E, ID>) ENUM_ID_OPS_BY_ENUM_CLASS.get(classE))
            .orElseThrow(() ->
                new IllegalStateException("unable to narrow EnumsIdOps<?, ?> for class " + classE.getName()));
      } finally {
        EXTENDED_CONTEXT_BY_ENUM_CLASS.remove(classE);
      }
    }
  }

  private final EnumsOps<E> enumsOps;
  private final Map<E, ID> orderedMapIdByEnumValue;
  private final Map<ID, E> orderedMapEnumValueById;
  private final Map<String, E> enumValueByValueOrIdOrAltToStringLowerCase;

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private EnumsIdOps(
      @NotNull Class<E> enumClassE,
      @NotNull Function<E, ID> fEToId,
      @NotNull Function<ID, String> fIdToString,
      @NotNull Optional<Function<E, String>> optionalFEToAltString
  ) {
    this.enumsOps = EnumsOps.from(enumClassE);
    var enumsValues =
        this.enumsOps
            .toList();
    this.orderedMapIdByEnumValue = StreamsOps.toMapOrderedUnmodifiableNonNulls(
        enumsValues.stream(),
        (e) ->
            Optional.of(entry(e, fEToId.apply(e))));
    this.orderedMapEnumValueById = MapsOps.swapOrdered(this.orderedMapIdByEnumValue);
    var idStringLowerCaseByEnumValue = this.orderedMapIdByEnumValue
        .entrySet()
        .stream()
        .map(enumValueAndId ->
            entry(
                enumValueAndId.getKey(),
                fIdToString.apply(enumValueAndId.getValue()).toLowerCase()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    var optionalAltStringLowerCaseByEnumValue = optionalFEToAltString
        .map(fEToAltString ->
            enumsValues
                .stream()
                .map(enumValue ->
                    entry(
                        enumValue,
                        fEToAltString.apply(enumValue).toLowerCase()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    //verify the lower case strings (lookup keys) are unique across all the enum value string sources
    var keyLowerCaseCollisions =
        enumsValues
            .stream()
            .flatMap(enumValue ->
                Stream.of(
                        Optional.of(enumValue.toString().toLowerCase()),
                        Optional.of(idStringLowerCaseByEnumValue.get(enumValue)),
                        optionalAltStringLowerCaseByEnumValue
                            .map(altStringLowerCaseByEnumValue ->
                                altStringLowerCaseByEnumValue.get(enumValue)))
                    .flatMap(Optional::stream))
            .collect(Collectors.groupingBy(Function.identity()))
            .values()
            .stream()
            .filter(eAsStrings ->
                eAsStrings.size() > 1)
            .flatMap(Collection::stream)
            .toList();
    if (!keyLowerCaseCollisions.isEmpty()) {
      throw new IllegalStateException(
          "invalid state for enum [%s] where the .toLowerCase() of enumValue.toString(), fIdToString.apply(fEToId.apply(enumValue)), and optionalFEToAltString.map(fEToAltString -> fEToAltString.apply(enumValue)).get() is not unique across all the enums values - erred values: %s".formatted(
              enumClassE.getSimpleName(),
              String.join(
                  EnumsOps.DEFAULT_SEPARATOR,
                  keyLowerCaseCollisions)));
    }
    this.enumValueByValueOrIdOrAltToStringLowerCase = Collections.unmodifiableMap(
        Stream.of(
                this.enumsOps.toOrderedMapByNameToLowerCase().entrySet().stream(),
                MapsOps.swap(idStringLowerCaseByEnumValue).entrySet().stream(),
                optionalAltStringLowerCaseByEnumValue
                    .stream()
                    .flatMap(altStringByEnumValue ->
                        MapsOps.swap(altStringByEnumValue).entrySet().stream()))
            .flatMap(Function.identity())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
  }

  @NotNull
  public EnumsOps<E> getEnumsOps() {
    return this.enumsOps;
  }

  @NotNull
  public Map<E, ID> getOrderedMapIdByEnumValue() {
    return this.orderedMapIdByEnumValue;
  }

  @NotNull
  public ID get(@NotNull E enumValue) {
    return getOrderedMapIdByEnumValue().get(enumValue);
  }

  @NotNull
  public Map<ID, E> getOrderedMapEnumValueById() {
    return this.orderedMapEnumValueById;
  }

  @NotNull
  public Optional<E> get(@NotNull ID id) {
    return Optional.ofNullable(getOrderedMapEnumValueById().get(id));
  }

  @NotNull
  public Map<String, E> getEnumValueByValueOrIdOrAltToStringLowerCase() {
    return this.enumValueByValueOrIdOrAltToStringLowerCase;
  }

  @NotNull
  public Optional<E> get(@NotNull String valueOrIdOrAltToString) {
    return Optional.ofNullable(getEnumValueByValueOrIdOrAltToStringLowerCase().get(valueOrIdOrAltToString.toLowerCase()));
  }
}
