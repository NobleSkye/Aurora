package com.aurora.command;

import com.aurora.AuroraMod;
import com.aurora.features.Feature;
import com.aurora.features.FeatureManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class AuroraCommands {
    
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            registerCommands(dispatcher);
        });
    }
    
    private static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        
        dispatcher.register(ClientCommandManager.literal("aurora")
            .then(ClientCommandManager.literal("toggle")
                .then(ClientCommandManager.argument("feature", StringArgumentType.string())
                    .executes(AuroraCommands::toggleFeature)))
            .then(ClientCommandManager.literal("enable")
                .then(ClientCommandManager.argument("feature", StringArgumentType.string())
                    .executes(AuroraCommands::enableFeature)))
            .then(ClientCommandManager.literal("disable")
                .then(ClientCommandManager.argument("feature", StringArgumentType.string())
                    .executes(AuroraCommands::disableFeature)))
            .then(ClientCommandManager.literal("list")
                .executes(AuroraCommands::listFeatures))
            .then(ClientCommandManager.literal("status")
                .executes(AuroraCommands::showStatus))
            .executes(AuroraCommands::showHelp));
    }
    
    private static int toggleFeature(CommandContext<FabricClientCommandSource> context) {
        String featureName = StringArgumentType.getString(context, "feature");
        FeatureManager manager = AuroraMod.getInstance().getFeatureManager();
        manager.toggleFeature(featureName);
        return 1;
    }
    
    private static int enableFeature(CommandContext<FabricClientCommandSource> context) {
        String featureName = StringArgumentType.getString(context, "feature");
        FeatureManager manager = AuroraMod.getInstance().getFeatureManager();
        manager.enableFeature(featureName);
        return 1;
    }
    
    private static int disableFeature(CommandContext<FabricClientCommandSource> context) {
        String featureName = StringArgumentType.getString(context, "feature");
        FeatureManager manager = AuroraMod.getInstance().getFeatureManager();
        manager.disableFeature(featureName);
        return 1;
    }
    
    private static int listFeatures(CommandContext<FabricClientCommandSource> context) {
        FeatureManager manager = AuroraMod.getInstance().getFeatureManager();
        FabricClientCommandSource source = context.getSource();
        
        source.sendFeedback(Text.literal("§6[Aurora] Available Features:"));
        for (Feature feature : manager.getAllFeatures().values()) {
            String status = feature.isEnabled() ? "§aEnabled" : "§cDisabled";
            source.sendFeedback(Text.literal("§f- " + feature.getName() + " " + status + " §7- " + feature.getDescription()));
        }
        return 1;
    }
    
    private static int showStatus(CommandContext<FabricClientCommandSource> context) {
        FeatureManager manager = AuroraMod.getInstance().getFeatureManager();
        FabricClientCommandSource source = context.getSource();
        
        source.sendFeedback(Text.literal("§6[Aurora] Current Status:"));
        int enabledCount = 0;
        for (Feature feature : manager.getAllFeatures().values()) {
            if (feature.isEnabled()) {
                enabledCount++;
                source.sendFeedback(Text.literal("§a✓ " + feature.getName()));
            }
        }
        source.sendFeedback(Text.literal("§f" + enabledCount + " features enabled"));
        return 1;
    }
    
    private static int showHelp(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        
        source.sendFeedback(Text.literal("§6[Aurora] Builder Mode Commands:"));
        source.sendFeedback(Text.literal("§f/aurora list §7- Show all features"));
        source.sendFeedback(Text.literal("§f/aurora status §7- Show enabled features"));
        source.sendFeedback(Text.literal("§f/aurora toggle <feature> §7- Toggle a feature"));
        source.sendFeedback(Text.literal("§f/aurora enable <feature> §7- Enable a feature"));
        source.sendFeedback(Text.literal("§f/aurora disable <feature> §7- Disable a feature"));
        source.sendFeedback(Text.literal("§7Features: noclip, fastplace, fastbreak, forceplace, replace, tinker, freezeupdates"));
        return 1;
    }
}
