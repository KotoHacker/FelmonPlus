package ru.aniby.felmon.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.text.TranslatableText;
import ru.aniby.felmon.utils.Functions;

import java.util.Arrays;

public class MainRPC {
    static final DiscordRPC instance = DiscordRPC.INSTANCE;
    public static final DiscordRichPresence presence = new DiscordRichPresence();

    public static void init() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        instance.Discord_Initialize("795331486332616754", handlers, true, null);
        presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
        menu();
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                instance.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void update() {
        instance.Discord_UpdatePresence(presence);
    }

    public static String getWorldState(String world, String biome) {
        String start = "rpc.ws.";
        switch (world) {
            case "overworld" -> {
                if (biome.contains("swamp"))
                    return start + world + ".swamp";
                else if (biome.contains("beach")) {
                    return (String) Functions.getRandomValue(Arrays.asList(
                            start + world + ".beach.a",
                            start + world + ".beach.b"
                    ));
                } else if (biome.contains("river")) {
                    return (String) Functions.getRandomValue(Arrays.asList(
                            start + world + ".river.a",
                            start + world + ".river.b"
                    ));
                } else if (biome.contains("ocean")) {
                    return (String) Functions.getRandomValue(Arrays.asList(
                            start + world + ".ocean.a",
                            start + world + ".ocean.b"
                    ));
                } else return (String) Functions.getRandomValue(Arrays.asList(
                        start + world + ".a",
                        start + world + ".b"
                ));
            }
            case "the_nether" -> {
                if (biome.contains("crimson_forest"))
                    return start + world + ".crimson_forest";
                else if (biome.contains("warped_forest"))
                    return start + world + ".warped_forest";
                else if (biome.contains("basalt_deltas"))
                    return start + world + ".basalt_deltas";
                else if (biome.contains("soul_sand_valley")) {
                    return (String) Functions.getRandomValue(Arrays.asList(
                            start + world + ".soul_sand_valley.a",
                            start + world + ".soul_sand_valley.b"
                    ));
                } else return (String) Functions.getRandomValue(Arrays.asList(
                        start + world + ".a",
                        start + world + ".b",
                        start + world + ".c"
                ));
            }
            case "the_end" -> {
                return start + world + ".a";
            }
        }
        return start + "null";
    }

    public static void menu() {
        presence.largeImageKey = "1";
        presence.largeImageText = "discord.gg/kmS8r5gefc";
        presence.smallImageKey = "iron_golem";
        presence.smallImageText = new TranslatableText("rpc.menu.small_image").getString();
        presence.details = new TranslatableText("rpc.menu.details").getString();
        presence.state = new TranslatableText("rpc.menu.state").getString();
        instance.Discord_UpdatePresence(presence);
    }
}
