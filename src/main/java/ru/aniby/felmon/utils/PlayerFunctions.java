package ru.aniby.felmon.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.Locale;
import java.util.Optional;

public class PlayerFunctions {
    public static String race = "";
    public static String clazz = "";
    public static String world = null;
    public static String biome = null;

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

    public static String[] getWorldAndBiome(MinecraftClient client) {
        String[] strings = new String[]{null, null};
        if (client.player != null && client.world != null) {
            strings[0] = client.world.getRegistryKey().getValue().getPath();

            Optional<RegistryKey<Biome>> entry = client.world.getBiome(client.player.getBlockPos()).getKey();
            entry.ifPresent(biomeRegistryKey -> strings[1] = biomeRegistryKey.getValue().getPath());
        }
        return strings;
    }
}
