package ru.aniby.felmon.utils;

import java.util.List;
import java.util.Random;

public class Functions {
    public static String readySplit(Object... objects) {
        StringBuilder str = new StringBuilder(objects[0].toString());
        for (int i = 1; i < objects.length; i++) {
            str.append(";").append(objects[i]);
        }
        return str.toString();
    }

    public static Object getRandomValue(List<Object> list) {
        return list.get(new Random().nextInt(list.size()));
    }
}
