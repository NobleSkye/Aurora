package com.aurora.client.util;

public class FastPlaceUtil {
    private static boolean fastPlaceEnabled = false;

    public static boolean isFastPlaceEnabled() {
        return fastPlaceEnabled;
    }

    public static void setFastPlaceEnabled(boolean enabled) {
        fastPlaceEnabled = enabled;
    }
}
