package ru.aniby.felmon.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.aniby.felmon.discord.MainRPC;
import ru.aniby.felmon.hook.Packets;
import ru.aniby.felmon.utils.PlayerFunctions;

@Mixin(MinecraftClient.class)
public class LeaveServerMixin {
    @Inject(at = @At("RETURN"), method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V")
    private void disconnect(Screen screen, CallbackInfo info) {
        PlayerFunctions.race = "";
        PlayerFunctions.clazz = "";
        PlayerFunctions.biome = null;
        PlayerFunctions.world = null;
        PlayerFunctions.gameMode = null;
        Packets.pluginUUID = null;
        MainRPC.menu();
    }
}