# Aurora - Creative Building Mod (Axiom-like)

## Overview
Aurora is a Fabric mod for Minecraft 1.21.4 designed to enhance creative mode building with powerful features similar to Axiom. The mod provides extensive tools for creative builders with features like extended reach, no-clip movement, instant operations, and various quality-of-life improvements.

## Features Implemented

### Movement & Navigation
1. **NoClip** - Pass through blocks freely with flight enabled
2. **ExtendedReach** - Reach blocks from up to 100 blocks away (configurable 1-1000)
3. **FlySpeed** - Fly 5x faster for quick navigation (configurable 1-20x)
4. **NoFallDamage** - Never take fall damage while building

### Building Tools
1. **FastPlace** - Place blocks instantly without delay
2. **FastBreak** - Break blocks instantly  
3. **ForcePlace** - Place blocks even where normally restricted
4. **Replace** - Replace blocks without breaking first (Keybind: R)
5. **Tinker** - Cycle through block states quickly (Keybind: L)

### Utilities
1. **FullBright** - Permanent night vision effect for clear visibility
2. **FreezeUpdates** - Freeze block updates for complex redstone work

## Controls

### GUI
- **Hold Left Alt** - Open feature toolbar
- **Left Alt + Number (1-9)** - Toggle features by number

### Keybinds
- **R** - Toggle Replace feature
- **L** - Toggle Tinker feature

### Commands
```
/aurora list            - List all available features
/aurora status          - Show status of all features  
/aurora toggle <name>   - Toggle a feature on/off
/aurora enable <name>   - Enable a specific feature
/aurora disable <name>  - Disable a specific feature
```

## GUI Features

### Active Features Display
- Shows enabled features in the top-left corner
- Color-coded by category:
  - **Cyan** - Movement features
  - **Green** - Building features
  - **Yellow** - Utility features

### Toolbar (Hold Left Alt)
- Shows all features organized by category
- Visual ON/OFF indicators with green/red borders
- Number keys for quick toggling
- Real-time status updates

## Technical Details

### Architecture
- **Entry Point**: `AuroraMod` - Initializes all systems
- **Feature System**: Abstract feature base with enable/disable lifecycle
- **Mixin-Based**: Most functionality implemented via Mixins for clean separation
- **Client-Side Only**: All features are client-side for creative building

### Code Structure
```
com.aurora/
├── AuroraMod.java              - Main mod entry point
├── command/
│   └── AuroraCommands.java     - Command registration
├── features/
│   ├── Feature.java            - Interface
│   ├── AbstractFeature.java    - Base implementation
│   ├── FeatureManager.java     - Feature registry & management
│   └── [Feature classes]       - Individual feature implementations
├── gui/
│   └── AuroraGui.java          - HUD and toolbar rendering
├── keybind/
│   └── AuroraKeybinds.java     - Keybind handling
└── mixin/
    └── [Mixin classes]         - Vanilla behavior modifications
```

### Building from Source
```bash
# Clean build
./gradlew clean build

# Run development client
./gradlew runClient
```

## Configuration

### Reach Distance
Modify in-game via ExtendedReachFeature:
```java
ExtendedReachFeature feature = AuroraMod.getInstance()
    .getFeatureManager()
    .getFeature(ExtendedReachFeature.class);
feature.setReachDistance(200.0); // 1-1000 blocks
```

### Fly Speed Multiplier
```java
FlySpeedFeature feature = AuroraMod.getInstance()
    .getFeatureManager()
    .getFeature(FlySpeedFeature.class);
feature.setSpeedMultiplier(10.0f); // 1-20x speed
```

## Requirements
- Minecraft 1.21.4
- Fabric Loader 0.16.9+
- Fabric API 0.119.2+
- Java 21

## License
See LICENSE file

## Contributing
Feel free to suggest new features or report issues!
