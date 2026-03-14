# StomUI

A lightweight, declarative menu library for Minestom servers with automatic Bedrock (Floodgate) support.

## Features

- **Unified API**: Write one menu, support both Java (Inventory) and Bedrock (Forms).
- **Kotlin DSL**: Clean, readable syntax.
- **High Performance**: Optimized for Minestom.
- **No Heavy Dependencies**: Minimal footprint.

## Documentation

Full documentation is available in the [`docs/`](docs/README.md) directory.

- [Getting Started](docs/getting-started.md)
- [Menu DSL](docs/concepts/menu-dsl.md)
- [Architecture](docs/concepts/architecture.md)

## Usage

Initialize the library in your server startup:

```kotlin
import codes.bed.minestom.menus.StomUI

fun main() {
    val server = MinecraftServer.init()
    
    // Initialize StomUI
    StomUI.init(MinecraftServer.getGlobalEventHandler())
    
    // ...
}
```


Creating a simple menu:

```kotlin
val menu = menu {
    title("My First Menu")
    size(InventoryType.CHEST_3_ROW)

    button {
        slots(13)
        item(Material.DIAMOND) {
            customName(Component.text("Click Me!"))
        }
        action {
            player.sendMessage("You clicked the diamond!")
            player.closeInventory()
        }
    }
}

// Open for player
menu.open(player)
```

For more docs, look into the docs repo
