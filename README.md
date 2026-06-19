# Paintball Game Simulator

## Overview
A turn-based paintball game simulator developed for the Algorithms and Data Structures course at FCT NOVA. Multiple teams compete on a 2D grid map by recruiting and deploying combat units to capture bunkers and eliminate opponents. The system is driven by a command-line interface and built entirely around custom data structures and the Iterator pattern.

## Core Technologies
Built entirely in Java with no external libraries. All data structures — fixed-capacity arrays, dynamic arrays with custom iterators, and a 2D cell grid — are implemented from scratch as part of the course requirements. The architecture follows an interface-segregated, facade-driven design to separate concerns between the CLI, the game system, and the domain objects.

## Key Capabilities

**Team & Bunker Management**: Up to eight teams compete on a configurable map. Each team owns a set of bunkers placed at fixed coordinates; bunkers generate passive income (treasury coins) each turn, which teams spend to recruit new combat units.

**Player Types**: Three distinct unit classes with unique attack patterns engage in rock-paper-scissors-style combat (Red beats Blue, Blue beats Green, Green beats Red). Same-type encounters always favour the attacker.
- **Blue**: Horizontal line-of-sight attack, alternating left and right
- **Green**: Simultaneous diagonal strike in all four directions
- **Red**: Sequential right-then-down sweep; can also chain multiple moves per turn

**Movement & Combat**: Players move one cell per command. Entering an enemy-occupied cell or a bunker held by an opponent triggers combat. The winner claims the cell; if a bunker is involved, ownership transfers to the winning team.

**Turn System**: Teams take turns in order; dead teams (no bunkers and no players) are skipped automatically. The game ends when only one team remains alive.

## Data Structures

| Structure | Implementation | Usage |
|---|---|---|
| Fixed Array | `Team[]` (MAX = 8) | Team registry |
| Dynamic Array | `Player[]`, `Bunker[]` (capacity-managed) | Players and bunkers per team |
| 2D Array | `Cell[][]` | Map grid |
| Custom Iterators | `TeamIterator`, `BunkerIterator`, `PlayerIterator`, `MapIterator` | Traversal across all collections |

## Architecture Highlights
- Interface-segregated design separates read and mutation contracts (`Game` / `GameSystem`, `Team`, `Bunker`, `Player`, `Cell`, `Map`)
- `GameSystemClass` acts as the central facade, delegating all operations to `GameClass`
- Polymorphic player hierarchy: `BluePlayerClass`, `GreenPlayerClass`, and `RedPlayerClass` each override the attack logic from the shared `PlayerClass` base
- `MapIteratorClass` supports team-filtered traversal for efficient map rendering
- Iterator pattern applied uniformly across Teams, Bunkers, Players, and the Map grid

## Project Structure

```
PaintballProject/
├── Main.java                        # Entry point and command-line interface
└── game/
    ├── GameSystem.java / GameSystemClass.java   # Facade layer
    ├── Game.java / GameClass.java               # Core game logic
    ├── Team.java / TeamClass.java
    ├── Bunker.java / BunkerClass.java
    ├── Map.java / MapClass.java
    ├── Cell.java / CellClass.java
    ├── Player.java / PlayerClass.java           # Base player class
    ├── BluePlayerClass.java                     # Horizontal attacker
    ├── GreenPlayerClass.java                    # Diagonal attacker
    ├── RedPlayerClass.java                      # Sequential attacker
    └── *IteratorClass.java (×4)                 # Custom iterators
```

## Getting Started

**Requirements:** Java 8 or higher

**Compile:**
```bash
javac -d bin $(find . -name "*.java")
```

**Run:**
```bash
java -cp bin Main
```

**Available Commands:**

| Command | Description |
|---|---|
| `game <w> <h> <t> <b>` | Initialise a new game (width, height, number of teams, number of bunkers) |
| `create <type> <bunker>` | Recruit a blue / green / red player from a bunker |
| `move <x> <y> <dirs>` | Move the player at (x, y) in the given directions (n / s / e / w) |
| `attack` | Trigger the current team's attack phase |
| `status` | Display map dimensions, total bunkers, and alive teams |
| `map` | Render the team-filtered map view |
| `bunkers` | List the current team's bunkers and their treasuries |
| `players` | List the current team's active players and positions |
| `help` | Show available commands |
| `quit` | Exit the program |
