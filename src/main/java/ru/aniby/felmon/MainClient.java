package ru.aniby.felmon;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.world.World;
import ru.aniby.felmon.discord.MainRPC;
import ru.aniby.felmon.hook.Keys;
import ru.aniby.felmon.hook.Packets;
import ru.aniby.felmon.hud.ui.AbilityManager;
import ru.aniby.felmon.hud.ui.ManaManager;
import ru.aniby.felmon.utils.PlayerFunctions;

import java.util.Objects;

public class MainClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Keys
        Keys.initList();
        // Tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Packets
            Packets.Listener(client);
            ClientPlayerEntity player = client.player;
            if (player != null) {
                World world = player.getWorld();
                String world_name = world.getRegistryKey().getValue().getPath();
                String biome_name = world.getBiome(player.getBlockPos()).getKey().get().getValue().getPath();
                if (!Objects.equals(PlayerFunctions.world, world_name) || !Objects.equals(PlayerFunctions.biome, biome_name)) {
                    MainRPC.setWorldState(new String[]{world_name, biome_name});
                }
                PlayerFunctions.inWater = player.getAir() < player.getMaxAir();
                ClientPlayNetworkHandler nhandler = MinecraftClient.getInstance().getNetworkHandler();
                if (nhandler != null) {
                    PlayerListEntry entry = nhandler.getPlayerListEntry(player.getGameProfile().getId());
                    if (entry != null)
                        PlayerFunctions.gameMode = entry.getGameMode();
                }
            }
            ManaManager.update();
            AbilityManager.update();
        });
        Packets.Reader();
    }
}
