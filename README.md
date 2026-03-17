# StomUI

A lightweight, declarative menu library for Minestom servers with automatic Bedrock (Floodgate) support.

## Features

- **Unified API**: Write one menu, support both Java (Inventory) and Bedrock (Forms).
- **Kotlin DSL**: Clean, readable syntax.
- **High Performance**: Optimized for Minestom.
- **No Heavy Dependencies**: Minimal footprint.

## Installation

Add the JitPack repository and dependency to your `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.bed-dev:stomui:Tag")
}
```

## Documentation

Full documentation is published at **https://stom.bed.codes**.

- StomUI docs: https://stom.bed.codes/stomui/
- Getting started: https://stom.bed.codes/stomui/getting-started
- Menu DSL: https://stom.bed.codes/stomui/concepts/menu-dsl

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

For more docs, visit https://stom.bed.codes/stomui/
