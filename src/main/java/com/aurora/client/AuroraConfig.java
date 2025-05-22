package com.aurora.client;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class AuroraConfig {
    private static boolean menuHoldToShow = true;
    private static final File CONFIG_FILE = new File("config/aurora.json");

    static {
        load();
    }

    public static boolean isMenuHoldToShow() {
        return menuHoldToShow;
    }

    public static void setMenuHoldToShow(boolean value) {
        menuHoldToShow = value;
        save();
    }

    public static void load() {
        if (!CONFIG_FILE.exists()) return;
        try (Reader reader = new FileReader(CONFIG_FILE)) {
            JsonObject obj = new Gson().fromJson(reader, JsonObject.class);
            if (obj != null && obj.has("menuHoldToShow")) {
                menuHoldToShow = obj.get("menuHoldToShow").getAsBoolean();
            }
        } catch (Exception ignored) {}
    }

    public static void save() {
        try (Writer writer = new FileWriter(CONFIG_FILE)) {
            JsonObject obj = new JsonObject();
            obj.addProperty("menuHoldToShow", menuHoldToShow);
            new Gson().toJson(obj, writer);
        } catch (Exception ignored) {}
    }
}
