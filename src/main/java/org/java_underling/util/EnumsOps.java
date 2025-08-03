package org.java_underling.util;

import org.java_underling.util.stream.StreamsOps;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

//TODO: fill out javadoc, including all methods
public final class EnumsOps<E extends Enum<E>> {

  public static final String DEFAULT_SEPARATOR = ", ";

  private static final Object ENUM_OPS_BY_ENUM_CLASS_SYNC = new Object();
  private static volatile Memoizer<Class<?>, EnumsOps<?>> ENUM_OPS_BY_ENUM_CLASS;

  @SuppressWarnings("unchecked")
  @NotNull
  public static <E extends Enum<E>> EnumsOps<E> from(@NotNull Class<E> enumClass) {
    if (ENUM_OPS_BY_ENUM_CLASS == null) {
      synchronized (ENUM_OPS_BY_ENUM_CLASS_SYNC) {
        if (ENUM_OPS_BY_ENUM_CLASS == null) {
          ENUM_OPS_BY_ENUM_CLASS = Memoizer.from(enumClassE ->
              new EnumsOps<>((Class<E>) enumClassE));
        }
      }
    }

    return (EnumsOps<E>) ENUM_OPS_BY_ENUM_CLASS.get(enumClass);
  }

  private static final Object ENUM_VALUE_BY_NAME_LOWER_CASE_BY_ENUM_CLASS_SYNC = new Object();
  private static volatile Memoizer<Class<?>, Map<String, ?>> ENUM_VALUE_BY_NAME_LOWER_CASE_BY_ENUM_CLASS;

  @NotNull
  private static <E extends Enum<E>> Map<String, E> toOrderedMapAsEnumByNameLowerCaseHelper(
      @NotNull Class<E> classEnum
  ) {
    return StreamsOps.toMapOrderedUnmodifiable(
        Arrays.stream(classEnum.getEnumConstants()),
        (e) ->
            Optional.of(entry(e.name().toLowerCase(), e)));
  }

  @SuppressWarnings("unchecked")
  @NotNull
  private static <E extends Enum<E>> Map<String, E> fetchCachedEnumInstanceByNameLowerCase(
      @NotNull Class<E> enumClass
  ) {
    if (ENUM_VALUE_BY_NAME_LOWER_CASE_BY_ENUM_CLASS == null) {
      synchronized (ENUM_VALUE_BY_NAME_LOWER_CASE_BY_ENUM_CLASS_SYNC) {
        if (ENUM_VALUE_BY_NAME_LOWER_CASE_BY_ENUM_CLASS == null) {
          ENUM_VALUE_BY_NAME_LOWER_CASE_BY_ENUM_CLASS = Memoizer.from(
              enumClassE -> toOrderedMapAsEnumByNameLowerCaseHelper((Class<E>) enumClassE));
        }
      }
    }

    return (Map<String, E>) ENUM_VALUE_BY_NAME_LOWER_CASE_BY_ENUM_CLASS.get(enumClass);
  }

  private final Class<E> enumClass;
  //wraps backing array copy from enumClass.getEnumConstants() with Collections.unmodifiableList()
  private final List<E> enumsValues;

  private EnumsOps(@NotNull Class<E> enumClass) {
    var enumsValues = Collections.unmodifiableList(Arrays.asList(enumClass.getEnumConstants()));
    //validate name().toLowerCase() are distinct as all 3 valueOfIgnoreCase* methods depend
    //  upon this assumption
    var enumValueCollisionsFromNameLowerCase =
        enumsValues
            .stream()
            .collect(Collectors.groupingBy(enumValue -> enumValue.name().toLowerCase()))
            .values()
            .stream()
            .filter(es -> es.size() > 1)
            .flatMap(Collection::stream)
            .toList();
    if (!enumValueCollisionsFromNameLowerCase.isEmpty()) {
      throw new IllegalStateException(
          "invalid state for enum [%s] where name().toLowerCase() is not unique across all the enums values - erred values: %s".formatted(
              enumClass.getSimpleName(),
              String.join(
                  DEFAULT_SEPARATOR,
                  enumsValues
                      .stream()
                      .filter(enumValueCollisionsFromNameLowerCase::contains)
                      .map(Enum::name)
                      .toList())));
    }
    this.enumClass = enumClass;
    this.enumsValues = enumsValues;
  }

  @NotNull
  public Class<E> getEnumClass() {
    return this.enumClass;
  }

  @NotNull
  public List<E> toList() {
    return this.enumsValues;
  }

  @NotNull
  public Stream<E> stream() {
    return this.enumsValues.stream();
  }

  @NotNull
  public Set<E> toOrderedSet() {
    return Collections.unmodifiableSet(EnumSet.allOf(this.enumClass));
  }

  @NotNull
  public Map<String, E> toOrderedMapByName() {
    return StreamsOps.toMapOrderedUnmodifiable(
        stream(),
        (e) ->
            Optional.of(entry(e.name(), e)));
  }

  @NotNull
  public Map<String, E> toOrderedMapByNameToLowerCase() {
    return fetchCachedEnumInstanceByNameLowerCase(this.enumClass);
  }

  public void forEach(@NotNull Consumer<E> consumer) {
    stream().forEach(consumer);
  }

  public void forEachOrdered(@NotNull Consumer<E> consumer) {
    stream().forEachOrdered(consumer);
  }

  @NotNull
  public Optional<E> valueOf(@NotNull String search) {
    return valueOfIgnoreCase(search);
  }

  @NotNull
  public E valueOfIgnoreCaseDefaultToFirstListed(
      @NotNull String search
  ) {
    return valueOfIgnoreCase(search, this.enumsValues.get(0));
  }

  @NotNull
  public E valueOfIgnoreCase(
      @NotNull String search,
      @NotNull E orElseDefault
  ) {
    return valueOfIgnoreCase(search)
        .orElse(orElseDefault);
  }

  @NotNull
  public Optional<E> valueOfIgnoreCase(
      @NotNull String search
  ) {
    return Optional.ofNullable(
        fetchCachedEnumInstanceByNameLowerCase(this.enumClass)
            .get(Objects.requireNonNull(search).toLowerCase()));
  }

  @NotNull
  public String join() {
    return join(DEFAULT_SEPARATOR);
  }

  @NotNull
  public String join(@NotNull String separator) {
    return join(Enum::name, separator);
  }

  @NotNull
  public String join(@NotNull Function<E, String> eToString) {
    return join(eToString, DEFAULT_SEPARATOR);
  }

  @NotNull
  public String join(
      @NotNull Function<E, String> eToString,
      @NotNull String separator
  ) {
    return join(toList(), eToString, separator);
  }

  @NotNull
  public String join(@NotNull Collection<E> es) {
    return join(es, Enum::name);
  }

  @NotNull
  public String join(
      @NotNull Collection<E> es,
      @NotNull Function<E, String> eToString
  ) {
    return join(es, eToString, DEFAULT_SEPARATOR);
  }

  @NotNull
  public String join(
      @NotNull Collection<E> es,
      @NotNull String separator
  ) {
    return join(es, Enum::name, separator);
  }

  @NotNull
  public String join(
      @NotNull Collection<E> es,
      @NotNull Function<E, String> eToString,
      @NotNull String separator
  ) {
    return String.join(
        separator,
        es.stream()
            .map(eToString)
            .toList());
  }
}
