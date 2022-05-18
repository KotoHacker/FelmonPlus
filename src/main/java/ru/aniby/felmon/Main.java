package ru.aniby.felmon;

import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aniby.felmon.discord.MainRPC;
import ru.aniby.felmon.hud.ui.AbilityManager;
import ru.aniby.felmon.hud.ui.ManaManager;
import ru.aniby.felmon.utils.Numbers;

public class Main implements ModInitializer {
	@Getter
	private static Main instance;
	public static final Logger LOGGER = LoggerFactory.getLogger("FELMON");

	@Override
	public void onInitialize() {
		instance = this;
		MainRPC.init();
		Numbers.init();

		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
			AbilityManager.render(matrixStack);
			ManaManager.render(matrixStack);
		});
	}
}
