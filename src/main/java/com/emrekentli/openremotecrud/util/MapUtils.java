package com.emrekentli.openremotecrud.util;

import java.util.Map;

public class MapUtils {

    private MapUtils() {
        // utility class, instantiation disabled
    }

    public static <K, V> void putIfNotNull(Map<K, V> map, K key, V value) {
        if (value != null) {
            map.put(key, value);
        }
    }
}
