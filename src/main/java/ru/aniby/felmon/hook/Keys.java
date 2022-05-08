package ru.aniby.felmon.hook;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import ru.aniby.felmon.utils.Numbers;

import java.util.*;

public class Keys {
    private static final List<KeyBinding> keys = new ArrayList<>();
    public static List<KeyBinding> getList() {
        return keys;
    }
    public static void initList() {
        String category = "category.felmon.binds";
        String start = "key.felmon.";
        for (KeyBinding keyBinding : new KeyBinding[]{
                new KeyBinding(start + "race.ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, category),
                new KeyBinding(start + "spell.first", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_1, category),
                new KeyBinding(start + "spell.second", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_2, category),
                new KeyBinding(start + "spell.third", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_3, category),
                new KeyBinding(start + "spell.fourth", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_4, category),
                new KeyBinding(start + "spell.fifth", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_5, category),
                new KeyBinding(start + "spell.sixth", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_6, category),
                new KeyBinding(start + "skill.first", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_7, category),
                new KeyBinding(start + "skill.second", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_8, category),
                new KeyBinding(start + "skill.third", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_9, category),
                new KeyBinding(start + "setting.show_model", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_0, category)
        }) {
            KeyBinding key = KeyBindingHelper.registerKeyBinding(keyBinding);
            keys.add(key);
        }
    }

    public static Map<String, String> getButtonValues(String translationKey) {
        String[] array = translationKey.replace("key.felmon.", "").split("\\.");
        Map<String, String> map = new HashMap<>();
        String type = array[0];
        String num = array[1];
        switch (type) {
            case "race", "skill", "spell" -> num = String.valueOf(Objects.equals(num, "ability") ? 0 : Numbers.getNumber(num));
        }
        map.put(type, num);
        return map;
    }

    public static KeyBinding getBind(String type, int number) {
        String translationKey = "key.felmon." + type + "." + (number == 0 ? "ability" : Numbers.getName(number));
        for (KeyBinding key : keys) {
            if (key.getTranslationKey().equalsIgnoreCase(translationKey))
                return key;
        }
        return null;
    }

    public static String getBindTranslate(String type, String setting) {
        switch (type) {
            case "race", "skill", "spell" -> {
                try {
                    int number = Integer.parseInt(setting);
                    return "key.felmon." + type + "." + (number == 0 ? "ability" : Numbers.getName(number));
                } catch (Exception ignored) {}
            }
            case "setting" -> {
                return "key.felmon.setting." + setting;
            }
        }
        return null;
    }
}
