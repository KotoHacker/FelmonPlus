package ru.aniby.felmon.hud.ui;

import net.minecraft.client.util.math.MatrixStack;

public class AbilityManager {
    private static int abilityLevel = 20;
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
        if (active)
            UserInterfaceUtil.render(matrixStack, 10, abilityLevel, "ability");
    }
}
