package ru.aniby.felmon.discord;

import net.minecraft.text.TranslatableText;
import ru.aniby.felmon.discord.rpclib.DiscordEventHandlers;
import ru.aniby.felmon.discord.rpclib.DiscordRPC;
import ru.aniby.felmon.discord.rpclib.DiscordRichPresence;
import ru.aniby.felmon.utils.Functions;
import ru.aniby.felmon.utils.PlayerFunctions;

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

    public static void setWorldState(String[] data) {
        assert data[0] != null;
        PlayerFunctions.world = data[0];
        PlayerFunctions.biome = data[1];
        MainRPC.presence.state = new TranslatableText(data[1] == null ? "rpc.ws.null" : MainRPC.getWorldState(data[0], data[1])).getString();
        MainRPC.presence.largeImageKey = data[0];
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
        presence.largeImageText = "play.felmon.xyz/discord";
        presence.smallImageKey = "iron_golem";
        String sit = new TranslatableText("rpc.menu.small_image").getString();
        presence.smallImageText = sit.equals("rpc.menu.small_image") ? "Offline" : sit;
        String details = new TranslatableText("rpc.menu.details").getString();
        presence.details = details.equals("rpc.menu.details") ? "In menu" : details;
        String state = new TranslatableText("rpc.menu.state").getString();
        presence.state = state.equals("rpc.menu.state") ? "Waiting for a miracle" : state;
        instance.Discord_UpdatePresence(presence);
    }
}
