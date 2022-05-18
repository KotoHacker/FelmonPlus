package ru.aniby.felmon.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameMode;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public class PlayerFunctions {
    public static String race = "";
    public static String clazz = "";
    public static String biome = null;
    public static String world = null;
    public static boolean inWater = false;
    public static GameMode gameMode = null;

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
