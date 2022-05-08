package ru.aniby.felmon.hook;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import ru.aniby.felmon.Main;
import ru.aniby.felmon.discord.MainRPC;
import ru.aniby.felmon.utils.Cooldowns;
import ru.aniby.felmon.utils.Functions;
import ru.aniby.felmon.utils.PlayerFunctions;

import java.util.Map;

public class Packets {
    private static final Identifier channel = new Identifier("felmon", "hook");
    public static String pluginUUID = null;

    public static void Listener(MinecraftClient client) {
        if (pluginUUID != null && !pluginUUID.isEmpty()) {
            for (KeyBinding key : Keys.getList()) {
                if (key.isPressed() && !(Cooldowns.getCooldown(key.getTranslationKey()) > 0)) {
                    Map<String, String> map = Keys.getButtonValues(key.getTranslationKey());
                    String type = map.keySet().toArray()[0].toString();
                    String setting = map.get(type);
                    send(1, Functions.readySplit(type, setting)); // Send
                }
            }
        }
        else if (pluginUUID == null && !client.isInSingleplayer() && MinecraftClient.getInstance().world != null)
            send(0);
    }

    public static void Reader() {
        // Reader
        ClientPlayNetworking.registerGlobalReceiver(channel, (client, handler, buf, responseSender) -> {
            ByteArrayDataInput in = ByteStreams.newDataInput(buf.getWrittenBytes());
            int id = in.readInt();
            if (pluginUUID == null && id == 0) {
                pluginUUID = in.readUTF();
                PlayerFunctions.race = in.readUTF();
                PlayerFunctions.clazz = in.readUTF();
            }
            else {
                switch (id) {
                    case -1 -> { // Button id (cooldown)
                        String type = in.readUTF(); // Type
                        String setting = in.readUTF(); // Number of button
                        long time = in.readLong(); // Cooldown time
                        String translate = Keys.getBindTranslate(type, setting);
                        if (translate != null)
                            Cooldowns.tryCooldown(translate, time);
                    }
                    case -2 -> { // get world and biome names(for Discord RPC)
                        String world = in.readUTF();
                        String biome = in.readUTF();
                        Main.LOGGER.info(world);
                        MainRPC.presence.state = new TranslatableText(MainRPC.getWorldState(world, biome)).getString();
                        MainRPC.presence.largeImageKey = world;
                        MainRPC.update();
                    }
                }
            }
        });
    }
    public static void send(Object... objects) {
        PacketByteBuf buf = PacketByteBufs.create();
        for (Object obj : objects) {
            if (obj instanceof Integer value)
                buf.writeInt(value);
            else if (obj instanceof String value)
                buf.writeString(value);
            else if (obj instanceof Double value)
                buf.writeDouble(value);
            else if (obj instanceof Float value)
                buf.writeFloat(value);
            else if (obj instanceof Boolean value)
                buf.writeBoolean(value);
            else if (obj instanceof Long value)
                buf.writeLong(value);
        }
        ClientPlayNetworking.send(channel, buf);
    }
}
