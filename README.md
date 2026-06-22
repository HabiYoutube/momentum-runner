# Momentum Runner

A client-side Fabric mod for Minecraft 1.21.1 that adds momentum-based movement mechanics inspired by speedrunning.

## Features

### Core Movement
- **Keybind**: Press **X** to toggle (configurable in Controls)
- **Instant Soul Speed III**: Movement starts at Soul Speed III equivalent speed
- **Progressive Momentum**: Continuously gains speed while moving
- **5 Speed Levels**: From Soul Speed III to ultra-fast maximum momentum

### Momentum System
- **Continuous Acceleration**: Speed increases the longer you move
- **Momentum Preservation**: Maintains momentum through normal movement
- **Momentum Loss**:
  - Stopping movement reduces momentum
  - Sneaking reduces momentum
  - Hitting walls reduces momentum
  - Large falls reduce momentum

### Terrain Traversal
- **Auto-Climb**: Automatically climb small blocks without jumping
- **Uphill Preservation**: Maintains momentum when moving uphill
- **Uneven Terrain**: Smooth movement across varied terrain
- **Obstacle Navigation**: Small obstacles don't interrupt movement

### Special Features
- **Water Running**: At maximum momentum, walk across water surfaces
- **Chat Notifications**: Receive messages when enabling/disabling
- **Smooth Acceleration**: Fluid speed progression feels natural
- **High Top Speed**: Extremely fast movement at maximum momentum

## Speed Levels

| Level | Status | Speed |
|-------|--------|-------|
| 1 | Soul Speed III | Baseline |
| 2 | Noticeably faster | +60% |
| 3 | Extremely fast | +140% |
| 4 | Very high speed | +240% |
| 5 | Maximum momentum | +380% |

## Installation

1. Download the latest JAR from [Releases](https://github.com/HabiYoutube/momentum-runner/releases)
2. Place it in your `.minecraft/mods` folder
3. Launch Minecraft with Fabric Loader
4. Press **X** to toggle Momentum Runner in-game

## Configuration

- **Toggle Key**: Default is **X**, configurable in Controls menu
- **Category**: Look under "Momentum Runner" in Controls

## Requirements

- Minecraft 1.21.1
- Fabric Loader 0.15+
- Fabric API

## Building

```bash
./gradlew build
```

The built JAR will be in `build/libs/`

## License

MIT License - See LICENSE file for details

## Credits

Inspired by momentum-based speedrunning mechanics and Minecraft's natural movement physics.