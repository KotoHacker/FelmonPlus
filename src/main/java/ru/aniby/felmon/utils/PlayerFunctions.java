package ru.aniby.felmon.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.TranslatableText;

import java.util.Locale;

public class PlayerFunctions {
    public static String race = "";
    public static String clazz = "";

    public static String getRaceTranslate() {
        return getTranslate("race." + race);
    }

    public static String getClassTranslate() {
        return getTranslate("class." + clazz);
    }

    public static String getTranslate(String path) {
        return new TranslatableText("felmon:"+path.toLowerCase(Locale.ROOT)).getString();
    }

    public static String getWorldName(MinecraftClient client) {
        ClientWorld world = client.world;
        if (world == null)
            return null;
        return world.getRegistryKey().getValue().toString();
    }

}
