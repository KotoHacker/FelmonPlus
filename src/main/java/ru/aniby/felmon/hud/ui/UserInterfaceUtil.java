package ru.aniby.felmon.hud.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;
import ru.aniby.felmon.utils.PlayerFunctions;

public class UserInterfaceUtil {
    public static void render(MatrixStack matrixStack, int minusY, int lvl, String texture) {
        Window s = MinecraftClient.getInstance().getWindow();
        if (s == null || PlayerFunctions.gameMode == GameMode.CREATIVE || PlayerFunctions.gameMode == GameMode.SPECTATOR) return;
        int x = s.getScaledWidth()/2+10;
        int y = s.getScaledHeight()-49-(PlayerFunctions.inWater ? 10 : 0)-minusY;

        for (int i = 0; i < 10; i++) {
            int u = 0;
            int j = (i+1)*2;
            if (lvl < j) {
                u = lvl + 1 == j ? 9 : 18;
            }
            RenderSystem.setShaderTexture(0, new Identifier("felmon", "textures/ui/"+texture+".png"));
            DrawableHelper.drawTexture(matrixStack, (x+i*8), y, 0, u, 0, 9, 9, 27, 9);
        }
    }
}
