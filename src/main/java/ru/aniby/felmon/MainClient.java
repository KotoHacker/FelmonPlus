package ru.aniby.felmon;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import ru.aniby.felmon.hook.Keys;
import ru.aniby.felmon.hook.Packets;

public class MainClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Keys
        Keys.initList();
        // Tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Packets
            Packets.Listener(client);
        });
        Packets.Reader();
    }
}
