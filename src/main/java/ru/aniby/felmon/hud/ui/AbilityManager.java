package ru.aniby.felmon.hud.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class AbilityManager {
    @Getter
    @Setter
    private static int abilityLevel = 20;
    @Getter
    @Setter
    private static int abilityTickTimer;

    private static boolean active = false;
    public static void setActive(boolean active) {
        AbilityManager.active = active;
    }

    public static void update() {
        if (active) {
            if (abilityLevel > 0 && abilityLevel <= 20) {
                ++abilityTickTimer;
                if (abilityTickTimer >= 60) {
                    abilityLevel = Math.max(abilityLevel - 1, 0);
                    abilityTickTimer = 0;
                }
            } else {
                active = false;
            }
        } else if (abilityLevel < 20) {
            ++abilityTickTimer;
            if (abilityTickTimer >= 60) {
                abilityLevel += 1;
                abilityTickTimer = 0;
            }
        }
    }

    public static void render(MatrixStack matrixStack) {
        if (!active)
            return;
        Window s = MinecraftClient.getInstance().getWindow();
        if (s == null) return;
        int x = s.getScaledWidth()/2-91;
        int y = s.getScaledHeight()-49;

        for (int i = 0; i < 10; i++) {
            int u = 0;
            int j = (i+1)*2;
            if (abilityLevel < j) {
                u = abilityLevel + 1 == j ? 9 : 18;
            }
            RenderSystem.setShaderTexture(0, new Identifier("felmon", "textures/ui/ability.png"));
            DrawableHelper.drawTexture(matrixStack, (x+i*8), y, 0, u, 0, 9, 9, 27, 9);
        }
    }
}
