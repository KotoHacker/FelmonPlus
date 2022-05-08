package ru.aniby.felmon.utils;

import java.util.HashMap;
import java.util.Map;

public class Cooldowns {
    // UUID / KeyBinding / Time
    private static final Map<Object, Long> cooldowns = new HashMap<>();

    /**
     * Retrieve the number of milliseconds left until a given cooldown expires.
     * <p>
     * Check for a negative value to determine if a given cooldown has expired. <br>
     * Cooldowns that have never been defined will return {@link Long#MIN_VALUE}.
     * @param key - for what object creates cooldown.
     * @return Number of milliseconds until the cooldown expires.
     */
    public static long getCooldown(Object key) {
        return calculateRemainder(cooldowns.get(key));
    }

    /**
     * Update a cooldown for the specified player.
     * @param key - for what object creates cooldown.
     * @param delay - number of milliseconds until the cooldown will expire again.
     * @return The previous number of milliseconds until expiration.
     */
    public static long setCooldown(Object key, long delay) {
        return calculateRemainder(
                cooldowns.put(key, System.currentTimeMillis() + delay));
    }

    /**
     * Determine if a given cooldown has expired. If it has, refresh the cooldown. If not, do nothing.
     * @param key - for what object creates cooldown.
     * @param delay - number of milliseconds until the cooldown will expire again.
     * @return TRUE if the cooldown was expired/unset and has now been reset, FALSE otherwise.
     */
    public static boolean tryCooldown(Object key, long delay) {
        if (getCooldown(key) <= 0) {
            setCooldown(key, delay);
            return true;
        }
        return false;
    }


    private static long calculateRemainder(Long expireTime) {
        return expireTime != null ? expireTime - System.currentTimeMillis() : Long.MIN_VALUE;
    }
}