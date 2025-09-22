package org.java_underling.util;

import org.java_underling.util.stream.StreamsOps;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

/**
 * An {@link EnumsOps} is a thread-safe immutable non-{@code null} utility class that produces a system-wide singleton
 * for augmenting the {@link Enum}'s mutable {@code values()} array by wrapping it in an unmodifiable {@link List}.
 * <p>
 * It also produces an internal cache for enabling a fast O(1) case-insensitive name search for a specific {@link Enum}
 * value (as opposed to the slower O(n) common pattern scanning through the mutable {@code values()} array).
 * <p>
 * The {@link EnumsOps} class may be added as a property directly to a newly defined {@link Enum}. And/or, it can be
 * used to wrap a pre-existing {@link Enum} which is unable to be directly enhanced/modified (for example, it is part of
 * a vendor SDK).
 * <p>
 * Both methods provide all the same functionality, augmentation, and enhancement. And because it is a system-wide
 * singleton, implementing both will result in the exact same instance being returned for both regardless of context.
 * <p>
 *
 * @param <E> type of the {@link Enum}
 */
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
    return StreamsOps.toMapOrderedUnmodifiableNonNulls(
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
    //validate name().toLowerCase() are distinct as all 3 valueOf* methods depend
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

  /**
   * Returns the {@link Class} of the enum being augmented.
   *
   * @return the {@link Class} of the enum being augmented.
   */
  @NotNull
  public Class<E> getEnumClass() {
    return this.enumClass;
  }

  /**
   * Returns the {@link Enum}'s mutable array {@code values} as an unmodifiable {@link List}.
   *
   * @return the {@link Enum}'s mutable array {@code values} as an unmodifiable {@link List}
   */
  @NotNull
  public List<E> toList() {
    return this.enumsValues;
  }

  /**
   * Returns the {@link Enum}'s mutable array {@code values} as a {@link Stream}.
   *
   * @return the {@link Enum}'s mutable array {@code values} as a {@link Stream}
   */
  @NotNull
  public Stream<E> stream() {
    return this.enumsValues.stream();
  }

  /**
   * Returns the {@link Enum}'s mutable array {@code values} as an unmodifiable {@link Set} specifically wrapping with
   * the highly performant {@link EnumSet}.
   *
   * @return the {@link Enum}'s mutable array {@code values} as an unmodifiable {@link Set} specifically wrapping with
   *     the highly performant {@link EnumSet}
   */
  @NotNull
  public Set<E> toOrderedSet() {
    return Collections.unmodifiableSet(EnumSet.allOf(this.enumClass));
  }

  /**
   * Returns the {@link Enum}'s mutable array {@code values} as an unmodifiable <i>ordered</i> {@link Map} where the key
   * is the {@link Enum#name()}, and the enum constant itself is the value.
   *
   * @return the {@link Enum}'s mutable array {@code values} as an unmodifiable <i>ordered</i> {@link Map} where the key
   *     is the {@link Enum#name()}, and the enum constant itself is the value
   */
  @NotNull
  public Map<String, E> toOrderedMapByName() {
    return StreamsOps.toMapOrderedUnmodifiableNonNulls(
        stream(),
        (e) ->
            Optional.of(entry(e.name(), e)));
  }

  /**
   * Returns the {@link Enum}'s mutable array {@code values} as an unmodifiable <i>ordered</i> {@link Map} where the key
   * is the <i>lower case</i> {@link Enum#name()}, and the enum constant itself is the value.
   *
   * @return the {@link Enum}'s mutable array {@code values} as an unmodifiable <i>ordered</i> {@link Map} where the key
   *     is the <i>lower case</i> {@link Enum#name()}, and the enum constant itself is the value
   */
  @NotNull
  public Map<String, E> toOrderedMapByNameToLowerCase() {
    return fetchCachedEnumInstanceByNameLowerCase(this.enumClass);
  }

  /**
   * Performs a non-interfering action for each element of this stream.
   *
   * @param consumer a non-interfering action to perform on the elements
   */
  public void forEach(@NotNull Consumer<E> consumer) {
    stream().forEach(consumer);
  }

  /**
   * Performs a non-interfering action for each element of this stream, in the encounter order of the stream if the
   * stream has a defined encounter order.
   *
   * @param consumer a non-interfering action to perform on the elements
   */
  public void forEachOrdered(@NotNull Consumer<E> consumer) {
    stream().forEachOrdered(consumer);
  }

  /**
   * Returns the case-insensitive search by name for the enum value, otherwise the first enum value in
   * {@link EnumsOps#toList}.
   *
   * @param search the name used to locate the enum value, case-insensitive
   * @return the case-insensitive search by name for the enum value, otherwise the first enum value in
   *     {@link EnumsOps#toList}
   */
  @NotNull
  public E valueOfOrDefaultToFirst(
      @NotNull String search
  ) {
    return valueOf(search, this.enumsValues.get(0));
  }

  /**
   * Returns the case-insensitive search by name for the enum value, otherwise the {@code orElseDefault}.
   *
   * @param search        the name used to locate the enum value, case-insensitive
   * @param orElseDefault the value to provide if the enum value cannot be found by its case-insensitive name
   * @return the case-insensitive search by name for the enum value, otherwise the {@code orElseDefault}
   */
  @NotNull
  public E valueOf(
      @NotNull String search,
      @NotNull E orElseDefault
  ) {
    return valueOf(search)
        .orElse(orElseDefault);
  }

  /**
   * Returns an {@link Optional} wrapping the case-insensitive search by name for the enum, otherwise an empty
   * {@link Optional}.
   *
   * @param search the name used to locate the enum value, case-insensitive
   * @return an {@link Optional} wrapping the case-insensitive search by name for the enum, otherwise an empty
   *     {@link Optional}
   */
  @NotNull
  public Optional<E> valueOf(
      @NotNull String search
  ) {
    return Optional.ofNullable(
        fetchCachedEnumInstanceByNameLowerCase(this.enumClass)
            .get(Objects.requireNonNull(search).toLowerCase()));
  }

  /**
   * Returns a new {@code String} composed of copies of the {@code Enum name} for all the enum values joined together
   * with a copy of the {@link EnumsOps#DEFAULT_SEPARATOR}.
   *
   * @return a new {@code String} composed of copies of the {@code Enum name} for all the enum values joined together
   *     with a copy of the {@link EnumsOps#DEFAULT_SEPARATOR}
   */
  @NotNull
  public String join() {
    return join(DEFAULT_SEPARATOR);
  }

  /**
   * Returns a new {@code String} composed of copies of the {@code Enum name} for all the enum values joined together
   * with a copy of the specified {@code separator}.
   *
   * @param separator the string used to separate the enum values
   * @return a new {@code String} composed of copies of the {@code Enum name} for all the enum values joined together
   *     with a copy of the specified {@code separator}
   */
  @NotNull
  public String join(@NotNull String separator) {
    return join(Enum::name, separator);
  }

  /**
   * Returns a new {@code String} composed of copies of the {@code Enum} transformed by the {@code eToString} function,
   * and joined together with a copy of the {@link EnumsOps#DEFAULT_SEPARATOR}.
   *
   * @param eToString the function to transform an enum value into a String
   * @return a new {@code String} composed of copies of the {@code Enum} transformed by the {@code eToString} function,
   *     and joined together with a copy of the {@link EnumsOps#DEFAULT_SEPARATOR}
   */
  @NotNull
  public String join(@NotNull Function<E, String> eToString) {
    return join(eToString, DEFAULT_SEPARATOR);
  }

  /**
   * Returns a new {@code String} composed of copies of the {@code Enum} transformed by the {@code eToString} function,
   * and joined together with a copy of the specified {@code separator}.
   *
   * @param eToString the function to transform an enum value into a String
   * @param separator the string used to separate the enum values
   * @return a new {@code String} composed of copies of the {@code Enum} transformed by the {@code eToString} function,
   *     and joined together with a copy of the specified {@code separator}
   */
  @NotNull
  public String join(
      @NotNull Function<E, String> eToString,
      @NotNull String separator
  ) {
    return join(toList(), eToString, separator);
  }

  /**
   * Returns a new {@code String} composed of copies of the {@link Enum#name()} of each of the provided {@link Enum}s
   * joined together with a copy of the {@link EnumsOps#DEFAULT_SEPARATOR}.
   *
   * @param es the list of enum values to use
   * @return a new {@code String} composed of copies of the {@link Enum#name()} of each of the provided {@link Enum}s
   *     joined together with a copy of the {@link EnumsOps#DEFAULT_SEPARATOR}
   */
  @NotNull
  public String join(@NotNull Collection<E> es) {
    return join(es, Enum::name);
  }

  /**
   * Returns a new {@code String} composed of copies of the provided {@code Enum}s transformed by the {@code eToString}
   * function, and joined together with a copy of the {@link EnumsOps#DEFAULT_SEPARATOR}.
   *
   * @param es        the list of enum values to use
   * @param eToString the function to transform an enum value into a String
   * @return a new {@code String} composed of copies of the provided {@code Enum}s transformed by the {@code eToString}
   *     function, and joined together with a copy of the {@link EnumsOps#DEFAULT_SEPARATOR}
   */
  @NotNull
  public String join(
      @NotNull Collection<E> es,
      @NotNull Function<E, String> eToString
  ) {
    return join(es, eToString, DEFAULT_SEPARATOR);
  }

  /**
   * Returns a new {@code String} composed of copies of the {@link Enum#name()} of each of the provided {@link Enum}s
   * joined together with a copy of the specified {@code separator}.
   *
   * @param es        the list of enum values to use
   * @param separator the string used to separate the enum values
   * @return a new {@code String} composed of copies of the {@link Enum#name()} of each of the provided {@link Enum}s
   *     joined together with a copy of the specified {@code separator}
   */
  @NotNull
  public String join(
      @NotNull Collection<E> es,
      @NotNull String separator
  ) {
    return join(es, Enum::name, separator);
  }

  /**
   * Returns a new {@code String} composed of copies of the provided {@code Enum}s transformed by the {@code eToString}
   * function, and joined together with a copy of the specified {@code separator}.
   *
   * @param es        the list of enum values to use
   * @param eToString the function to transform an enum value into a String
   * @param separator the string used to separate the enum values
   * @return a new {@code String} composed of copies of the provided {@code Enum}s transformed by the {@code eToString}
   *     function, and joined together with a copy of the specified {@code separator}
   */
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
