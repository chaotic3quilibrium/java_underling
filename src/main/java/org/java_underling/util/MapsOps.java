package org.java_underling.util;

import org.java_underling.lang.ParametersValidationException;
import org.java_underling.util.refined.NonEmptyMap;
import org.java_underling.util.stream.StreamsOps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility class providing static methods to create {@link Map} instances.
 */
public class MapsOps {

  private MapsOps() {
    throw new UnsupportedOperationException("suppressing class instantiation");
  }

  /**
   * Returns an empty {@link Map} using {@link Map#of}, if {@code mapKv} is {@code null}, otherwise returns
   * {@code mapKv}.
   * <p>
   * Note: If the requirement is to both avoid a {@code null} value, and an empty {@link Map}, the refined class of
   * {@link NonEmptyMap} enable <i>compile-time enforcement</i> of said contract requirements.
   *
   * @param mapKv possibly {@code null} {@link Map} to reify to make {@code null} safe
   * @param <K>   the type of the key instances contained in the {@link Map}
   * @param <V>   the type of the value instances contained in the {@link Map}
   * @return an empty {@link Map} using {@link Map#of}, if {@code mapKv} is {@code null}, otherwise returns
   *     {@code mapKv}.
   */
  @NotNull
  public static <K, V> Map<K, V> nullToEmpty(@Nullable Map<K, V> mapKv) {
    return mapKv != null
        ? mapKv
        : Map.of();
  }

  /**
   * Returns a non-empty {@link Optional} containing an instance of {@link ParametersValidationException} that itemizes
   * the validation preconditions which failed, otherwise an {@link Optional#empty()}.
   * <p>
   * <u><b>Preconditions:</b></u>
   * <ul>
   * <li>{@code entry.getKey()} must not be {@code null}</li>
   * <li>{@code entry.getValue()} must not be {@code null}</li>
   * </ul>
   * <p>
   *
   * @param entry the key/value pair as an {@link Entry}
   * @param <K>   the type of the keys contained in the {@code map}
   * @param <V>   the type of the values contained in the {@code map}
   * @return a non-empty {@link Optional} containing an instance of {@link ParametersValidationException} that itemizes
   *     the validation preconditions which failed, otherwise an {@link Optional#empty()}
   */
  public static <K, V> Optional<ParametersValidationException> containsNulls(
      @NotNull Entry<K, V> entry
  ) {
    return (entry.getKey() == null) || (entry.getValue() == null)
        //@formatter:off
        ? Optional.of(
            new ParametersValidationException(
                "MapsOps.containsNulls failed preconditions on the entry",
                (entry.getKey() == null) && (entry.getValue() == null)
                    ? List.of(
                        "entry.getKey() is null",
                        "entry.getValue() is null")
                    : List.of(
                        entry.getKey() == null
                              ? "entry.getKey() is null"
                              : "entry.getValue() is null")))
        : Optional.empty();
        //@formatter:on
  }

  /**
   * Returns a new unmodifiable unordered {@link Map} from an existing {@code map}, adding/updating an {@link Entry}.
   *
   * @param map   the source of the existing key/value pairs
   * @param entry the key/value pair as an {@link Entry}
   * @param <K>   the type of the keys contained in the {@code map}
   * @param <V>   the type of the values contained in the {@code map}
   * @return a new unmodifiable unordered {@link Map} from an existing {@code map}, adding/updating an {@link Entry}
   * @throws NullPointerException if the provided {@link Entry} contains {@code null} in either its key or value.
   */
  @NotNull
  public static <K, V> Map<K, V> addEntry(
      @NotNull Map<K, V> map,
      @NotNull Entry<K, V> entry
  ) {
    containsNulls(entry)
        .ifPresent(parametersValidationException -> {
          throw parametersValidationException;
        });

    return addKeyAndValue(map, entry.getKey(), entry.getValue());
  }

  /**
   * Returns a new unmodifiable unordered {@link Map} from an existing {@code map}, adding/updating a {@code key} and
   * its associated {@code value}.
   *
   * @param map   the source of the existing key/value pairs
   * @param key   the key with which to associate with the value
   * @param value the value with which to associate with the key
   * @param <K>   the type of the keys contained in the {@code map}
   * @param <V>   the type of the values contained in the {@code map}
   * @return a new unmodifiable unordered {@link Map} from an existing {@code map}, adding/updating a {@code key} and
   *     its associated {@code value}
   */
  @NotNull
  public static <K, V> Map<K, V> addKeyAndValue(
      @NotNull Map<K, V> map,
      @NotNull K key,
      @NotNull V value
  ) {
    var result = new HashMap<>(map);
    result.put(key, value);

    return Collections.unmodifiableMap(result);

  }

  /**
   * Returns a new unmodifiable ordered {@link Map} from an existing {@code map}, appending (or if the key is already
   * present, updating) an {@link Entry}.
   *
   * @param map   the source of the existing key/value pairs
   * @param entry the key/value pair as an {@link Entry}
   * @param <K>   the type of the keys contained in the {@code map}
   * @param <V>   the type of the values contained in the {@code map}
   * @return a new unmodifiable ordered {@link Map} from an existing {@code map}, appending (or if the key is already
   *     present, updating) an {@link Entry}
   * @throws NullPointerException if the provided {@link Entry} contains {@code null} in either its key or value.
   */
  @NotNull
  public static <K, V> Map<K, V> appendEntry(
      @NotNull Map<K, V> map,
      @NotNull Entry<K, V> entry
  ) {
    containsNulls(entry)
        .ifPresent(parametersValidationException -> {
          throw parametersValidationException;
        });

    return appendKeyAndValue(map, entry.getKey(), entry.getValue());
  }

  /**
   * Returns a new unmodifiable ordered {@link Map} from an existing {@code map}, appending (or if the key is already
   * present, updating) a {@code key} and its associated {@code value}.
   *
   * @param map   the source of the existing key/value pairs
   * @param key   the key with which to associate with the value
   * @param value the value with which to associate with the key
   * @param <K>   the type of the keys contained in the {@code map}
   * @param <V>   the type of the values contained in the {@code map}
   * @return a new unmodifiable ordered {@link Map} from an existing {@code map}, appending (or if the key is already
   *     present, updating) a {@code key} and its associated {@code value}
   */
  public static <K, V> Map<K, V> appendKeyAndValue(
      @NotNull Map<K, V> map,
      @NotNull K key,
      @NotNull V value
  ) {
    if (!map.isEmpty()) {
      var result = new LinkedHashMap<>(map);
      result.put(key, value);

      return Collections.unmodifiableMap(result);
    }

    return Map.of(key, value);
  }

  /**
   * Returns an unmodifiable unordered map consisting of each map, filtered to non-null in both the keys and the values,
   * from maps added together.
   *
   * @param maps the sets to append
   * @param <K>  the type of the keys contained in the {@code map}
   * @param <V>  the type of the values contained in the {@code map}
   * @return an unmodifiable unordered map consisting of each map ,filtered to non-null in both the keys and the values,
   *     from maps added together
   */
  @SuppressWarnings("ConstantValue")
  @NotNull
  @SafeVarargs
  public static <K, V> Map<K, V> addMaps(
      @NotNull Map<K, V>... maps
  ) {
    if (maps.length > 0) {
      var result = new HashMap<K, V>();
      IntStream.range(0, maps.length)
          .forEach(index -> {
            var map = maps[index];
            if (map != null) {
              var resolvedMap =
                  map.entrySet()
                      .stream()
                      .filter(entry ->
                          containsNulls(entry).isEmpty())
                      .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
              if (!resolvedMap.isEmpty()) {
                result.putAll(resolvedMap);
              }
            }
          });

      return !result.isEmpty()
          ? Collections.unmodifiableMap(result)
          : Map.of();
    }

    return Map.of();
  }

  /**
   * Returns an unmodifiable ordered map consisting of each map, filtered to non-null in both the keys and the values,
   * from maps appended together.
   *
   * @param maps the sets to append
   * @param <K>  the type of the keys contained in the {@code map}
   * @param <V>  the type of the values contained in the {@code map}
   * @return an unmodifiable unordered map consisting of each map, filtered to non-null in both the keys and the values,
   *     from maps added together
   */
  @SuppressWarnings("ConstantValue")
  @NotNull
  @SafeVarargs
  public static <K, V> Map<K, V> appendMaps(
      @NotNull Map<K, V>... maps
  ) {
    if (maps.length > 0) {
      var result = new LinkedHashMap<K, V>();
      IntStream.range(0, maps.length)
          .forEach(index -> {
            var map = maps[index];
            if (map != null) {
              var resolvedMap = StreamsOps.toMapOrderedUnmodifiableNonNulls(map.entrySet().stream());
              if (!resolvedMap.isEmpty()) {
                result.putAll(resolvedMap);
              }
            }
          });

      return !result.isEmpty()
          ? Collections.unmodifiableMap(result)
          : Map.of();
    }

    return Map.of();
  }

  /**
   * Returns an unmodifiable unordered map where the keys and values have been swapped.
   * <p>
   * ---
   * <p>
   * Swaps a Map where, for each entry, the value becomes the key, and the key becomes the value. In the event the
   * values in the supplied Map are not unique, i.e. there is more than one key with the same value (according to
   * {@link Object#equals}), it is undefined which unique key is retained as a value, and which other keys are silently
   * dropped.
   *
   * @param map the map in which every entry will swap the key and value
   * @param <K> the type of the key instances
   * @param <V> the type of the value instances
   * @return an unmodifiable unordered map where the keys and values have been swapped
   * @throws NullPointerException if an {@link Entry} contains {@code null} in either its key or value.
   */
  @NotNull
  public static <K, V> Map<V, K> swap(
      @NotNull Map<K, V> map
  ) {
    return !map.isEmpty()
        //@formatter:off
        ? Collections.unmodifiableMap(map
            .entrySet()
            .stream()
            .peek(entry ->
                containsNulls(entry)
                    .ifPresent(parametersValidationException -> {
                      throw parametersValidationException;
                    }))
            .collect(Collectors.toMap(Entry::getValue, Entry::getKey)))
        : Map.of();
        //@formatter:on
  }

  /**
   * Returns an unmodifiable <i>ordered</i> map where the keys and values have been swapped.
   * <p>
   * ---
   * <p>
   * Swaps a Map where, for each entry, the value becomes the key, and the key becomes the value. In the event the
   * values in the supplied Map are not unique, i.e. there is more than one key with the same value (according to
   * {@link Object#equals}), it is undefined which unique key is retained as a value, and which other keys are silently
   * dropped.
   *
   * @param map the map in which every entry will swap the key and value
   * @param <K> the type of the key instances
   * @param <V> the type of the value instances
   * @return an unmodifiable unordered map where the keys and values have been swapped
   * @throws NullPointerException if an {@link Entry} contains {@code null} in either its key or value.
   */
  @NotNull
  public static <K, V> Map<V, K> swapOrdered(
      @NotNull Map<K, V> map
  ) {
    if (!map.isEmpty()) {
      var result = new LinkedHashMap<V, K>();
      //noinspection SimplifyStreamApiCallChains
      map.entrySet()
          .stream()
          .forEachOrdered(
              entry -> {
                containsNulls(entry)
                    .ifPresent(parametersValidationException -> {
                      throw parametersValidationException;
                    });
                result.put(entry.getValue(), entry.getKey());
              });

      return Collections.unmodifiableMap(result);
    }

    return Map.of();
  }
}
