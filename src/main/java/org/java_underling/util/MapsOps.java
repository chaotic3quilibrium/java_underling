package org.java_underling.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

//TODO: fill out javadoc
public class MapsOps {

  private MapsOps() {
    throw new UnsupportedOperationException("suppressing class instantiation");
  }

  /**
   * Returns an empty map using {@link Map#of}, if {@code mapKv} is {@code null}, otherwise returns {@code mapKv}.
   *
   * @param mapKv the map to reify to make null safe
   * @param <K>   the type of the key instances contained in the {@link Map}
   * @param <V>   the type of the value instances contained in the {@link Map}
   * @return an empty map using {@link Map#of}, if {@code mapKv} is {@code null}, otherwise returns {@code mapKv}
   */
  @NotNull
  public static <K, V> Map<K, V> nullToEmpty(@Nullable Map<K, V> mapKv) {
    return mapKv != null
        ? mapKv
        : Map.of();
  }

  public static <K, V> Optional<String> containsNulls(
      @NotNull Entry<K, V> entry
  ) {
    return (entry.getKey() == null) || (entry.getValue() == null)
        //@formatter:off
        ? Optional.of(
            (entry.getKey() == null) && (entry.getValue() == null)
                ? "entry.getKey() and entry.getValue() are null"
                : entry.getKey() == null
                    ? "entry.getKey() is null"
                    : "entry.getValue() is null")
        : Optional.empty();
        //@formatter:on
  }

  /**
   * Return a new {@link Map} from an existing {@code map}, adding/updating an {@link Entry}.
   *
   * @param map   the source of the existing key/value pairs
   * @param entry the key/value pair as an {@link Entry}
   * @param <K>   the type of the keys contained in the {@code map}
   * @param <V>   the type of the values contained in the {@code map}
   * @return a new {@link Map} from an existing {@code map}, adding/updating an {@link Entry}
   * @throws NullPointerException if the provided {@link Entry} contains {@code null} in either its key or value.
   */
  @NotNull
  public static <K, V> Map<K, V> add(
      @NotNull Map<K, V> map,
      @NotNull Entry<K, V> entry
  ) {
    containsNulls(entry)
        .ifPresent(message -> {
          throw new NullPointerException(message);
        });

    return add(map, entry.getKey(), entry.getValue());
  }

  /**
   * Return a new {@link Map} from an existing {@code map}, adding/updating a {@code key} and its associated
   * {@code value}.
   *
   * @param map   the source of the existing key/value pairs
   * @param key   the key with which to associate with the value
   * @param value the value with which to associate with the key
   * @param <K>   the type of the keys contained in the {@code map}
   * @param <V>   the type of the values contained in the {@code map}
   * @return a new {@link Map} from an existing {@code map}, adding/updating a {@code key} and its associated
   *     {@code value}
   */
  @NotNull
  public static <K, V> Map<K, V> add(
      @NotNull Map<K, V> map,
      @NotNull K key,
      @NotNull V value
  ) {
    var result = new HashMap<>(map);
    result.put(key, value);

    return Collections.unmodifiableMap(result);

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
                    .ifPresent(message -> {
                      throw new NullPointerException(message);
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
                    .ifPresent(message -> {
                      throw new NullPointerException(message);
                    });
                result.put(entry.getValue(), entry.getKey());
              });

      return Collections.unmodifiableMap(result);
    }

    return Map.of();
  }
}
