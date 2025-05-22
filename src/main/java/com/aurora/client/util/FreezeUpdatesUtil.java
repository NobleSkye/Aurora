package com.aurora.client.util;

public class FreezeUpdatesUtil {
    private static boolean freezeUpdatesEnabled = false;

    public static boolean areFreezeUpdatesEnabled() {
        return freezeUpdatesEnabled;
    }

    public static void setFreezeUpdatesEnabled(boolean enabled) {
        freezeUpdatesEnabled = enabled;
    }
}
