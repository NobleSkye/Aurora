package com.aurora;

import com.aurora.command.AuroraCommands;
import com.aurora.features.FeatureManager;
import com.aurora.gui.AuroraGui;
import com.aurora.keybind.AuroraKeybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuroraMod implements ClientModInitializer {
    public static final String MOD_ID = "aurora";
    public static final String MOD_NAME = "Aurora";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    
    private static AuroraMod instance;
    private FeatureManager featureManager;
    private AuroraGui gui;
    private AuroraKeybinds keybinds;
    
    @Override
    public void onInitializeClient() {
        instance = this;
        System.out.println("=== AURORA MOD STARTING ===");
        LOGGER.info("Initializing Aurora Mod v1.0.0");
        
        // Initialize core systems
        this.featureManager = new FeatureManager();
        this.gui = new AuroraGui();
        this.keybinds = new AuroraKeybinds();
        
        // Register commands
        AuroraCommands.register();
        
        // Register tick events
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            featureManager.tick();
            keybinds.handleInput();
        });
        
        System.out.println("=== AURORA MOD INITIALIZED ===");
        LOGGER.info("Aurora Mod initialized successfully!");
    }
    
    public static AuroraMod getInstance() {
        return instance;
    }
    
    public FeatureManager getFeatureManager() {
        return featureManager;
    }
    
    public AuroraGui getGui() {
        return gui;
    }
    
    public AuroraKeybinds getKeybinds() {
        return keybinds;
    }
}
