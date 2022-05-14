package ru.aniby.felmon.mixin;

import com.mojang.authlib.GameProfile;
import lombok.SneakyThrows;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.telemetry.TelemetrySender;
import net.minecraft.network.ClientConnection;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.aniby.felmon.discord.MainRPC;
import ru.aniby.felmon.utils.PlayerFunctions;

import java.util.Locale;

@Mixin(ClientPlayNetworkHandler.class)
public class JoinServerMixin {
    @SneakyThrows
    @Inject(at = @At("RETURN"), method = "<init>")
    private void onServerJoin(MinecraftClient client, Screen screen, ClientConnection connection, GameProfile profile, TelemetrySender telemetrySender, CallbackInfo ci) {
        new Thread(() -> {
            while (PlayerFunctions.race.isEmpty() || PlayerFunctions.clazz.isEmpty() || client.world == null || client.player == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
            String image = PlayerFunctions.race.toLowerCase(Locale.ROOT);
            if (image.equals("human"))
                image = "steve";
            String race = PlayerFunctions.getRaceTranslate();
            MainRPC.presence.smallImageKey = image;
            MainRPC.presence.smallImageText = race + (PlayerFunctions.clazz.equals("STANDART") ? "" : "-" + PlayerFunctions.getClassTranslate());
            MainRPC.presence.details = new TranslatableText("rpc.player").getString() + ": " + profile.getName();
            MainRPC.setWorldState(PlayerFunctions.getWorldAndBiome(client));
            MainRPC.update();
        }, "PlayerDataGetter").start();
    }
}