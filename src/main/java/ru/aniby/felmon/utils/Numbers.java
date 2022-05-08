package ru.aniby.felmon.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Numbers {
    private static final Map<String, Integer> map = new HashMap<>();
    public static void init() {
        map.put("zero", 0);
        map.put("first", 1);
        map.put("second", 2);
        map.put("third", 3);
        map.put("fourth", 4);
        map.put("fifth", 5);
        map.put("sixth", 6);
    }

    public static String getName(Integer number) {
        for (String key : map.keySet()) {
            if (Objects.equals(map.get(key), number))
                return key;
        }
        return null;
    }

    public static Integer getNumber(String name) {
        return map.get(name) == null ? 0 : map.get(name);
    }
}
