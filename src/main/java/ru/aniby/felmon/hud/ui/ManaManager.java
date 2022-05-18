package ru.aniby.felmon.hud.ui;

import net.minecraft.client.util.math.MatrixStack;

public class ManaManager {
    private static double mana = 1;
    private static int manaTickTimer;
    private static double maximum = 1;

    public static void update() {
        if (mana < maximum) {
            ++manaTickTimer;
            if (manaTickTimer >= 20) {
                mana += .5;
                manaTickTimer = 0;
            }
        }
    }

    public static void render(MatrixStack matrixStack) {
        int lvl = (int) Math.round(mana * 20 / maximum);
        UserInterfaceUtil.render(matrixStack, 0, lvl, "mana");
    }

    public static void removeMana(double amount) {
        mana -= amount;
    }
    public static void setMaximum(double max) {
        maximum = max;
    }
    public static double getMaximum() {
        return maximum;
    }
    public static void setMana(double _mana) {
        mana = _mana;
    }

}
