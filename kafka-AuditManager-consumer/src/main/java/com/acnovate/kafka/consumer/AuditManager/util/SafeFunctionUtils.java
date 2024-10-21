package com.acnovate.kafka.consumer.AuditManager.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Helper util intended to ease not NULL safe operations.
 */
public final class SafeFunctionUtils {

    private SafeFunctionUtils() {
    }

    /**
     * @param t      possible {@code null} value
     * @param action action to be performed given {@code t} is not null
     */
    public static <T> void getSafe(T t, Consumer<? super T> action) {
        Optional.ofNullable(t).ifPresent(action);
    }

    /**
     * @param t      possible {@code null} collection
     * @param action action to be applied to first {@code t} element
     */
    public static <T> void getFirstSafe(Collection<T> t, Consumer<? super T> action) {
        Optional.ofNullable(t)
                .ifPresent(collection -> collection.stream().filter(Objects::nonNull).findFirst().ifPresent(action));
    }

    /**
     * @param t      possible {@code null} value collection
     * @param action action to be applied to each collection element, given
     *               {@code t} is not null
     */
    public static <T> void iterateSafe(Collection<T> t, Consumer<? super T> action) {
        Optional.ofNullable(t).ifPresent(collection -> collection.forEach(action));
    }

    /**
     * @param t      possible {@code null} value {@code Map}
     * @param action action to be applied to each collection element, given
     *               {@code t} is not null
     */
    public static <K, V> void iterateSafe(Map<K, V> t, BiConsumer<K, V> action) {
        Optional.ofNullable(t).ifPresent(map -> map.forEach(action));
    }

    /**
     * @param t      possible {@code null} value collection
     * @param filter predicate that filters the collection
     * @param action action to be applied to each collection element, given
     *               {@code t} is not null
     */
    public static <T> void iterateSafeWithFilter(Collection<T> t, Predicate<T> filter, Consumer<? super T> action) {
        Optional.ofNullable(t).ifPresent(collection -> collection.stream().filter(filter).forEach(action));
    }

    /**
     * @param root
     * @param getter
     * @param getterOfTheGetter
     * @param log
     * @param errorMessage
     * @param <T1>
     * @param <T2>
     * @return
     */
    public static <T1, T2> String extractGeneric(T1 root, Function<T1, T2> getter,
                                                 Function<T2, String> getterOfTheGetter, Logger log, String errorMessage) {
        final T2 obj = getter.apply(root);
        String result = null;

        if (obj != null) {
            String value = getterOfTheGetter.apply(obj);
            if (StringUtils.isNotBlank(value)) {
                result = value;
            }
        }

        if (result == null) {
            log.error(errorMessage, root);
        }

        return result;
    }

}
