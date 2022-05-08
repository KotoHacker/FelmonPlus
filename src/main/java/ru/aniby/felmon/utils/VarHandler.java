package ru.aniby.felmon.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.world.ClientWorld;

import java.util.HashMap;
import java.util.Map;

public class VarHandler {
    private static Map<String, String> variables;

    public static void update() {
        variables = new HashMap<>();
        ServerInfo entry = MinecraftClient.getInstance().getCurrentServerEntry();
        if (entry != null) {
            String playerCount = entry.playerCountLabel.asString();
            if (playerCount == null || playerCount.isEmpty()) playerCount = "-1";
            variables.put("playerCount", playerCount);

            ClientWorld world = MinecraftClient.getInstance().world;
            variables.put("world", (world == null ? "empty" : world.getRegistryKey().getValue().toString()));
        } else {
            variables.put("playerCount", "-1");
        }
    }

    public static String replace(String key) {
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            key = key.replace("$"+entry.getKey(), entry.getValue());
        }
        return key;
    }

    public static String get(String name) {
        return variables.get(name);
    }
}