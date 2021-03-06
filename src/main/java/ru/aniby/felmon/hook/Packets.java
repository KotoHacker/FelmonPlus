package ru.aniby.felmon.hook;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import ru.aniby.felmon.Main;
import ru.aniby.felmon.hud.ui.AbilityManager;
import ru.aniby.felmon.hud.ui.ManaManager;
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
//        else if (pluginUUID == null && !client.isInSingleplayer() && MinecraftClient.getInstance().world != null)
//            send(0);
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
                ManaManager.setMaximum(in.readDouble());
                ManaManager.setMana(in.readDouble());
                send(0, pluginUUID);
                Main.LOGGER.info("send: " + pluginUUID);
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
                    case -2 -> { // ability enable/disable
                        boolean status = in.readBoolean();
                        AbilityManager.setActive(status);
                    }
                    case -3 -> { // update data
                        String type = in.readUTF(); // race / class / maximum_mana / mana
                        switch (type) {
                            case "race" -> PlayerFunctions.race = in.readUTF(); // race value
                            case "class" -> PlayerFunctions.clazz = in.readUTF(); // class value
                            case "maximum_mana" -> ManaManager.setMaximum(in.readDouble()); // maximum mana
                            case "remove_mana" -> ManaManager.removeMana(in.readDouble()); // mana to remove
                            case "set_mana" -> ManaManager.setMana(in.readDouble()); // mana to remove
                        }
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
