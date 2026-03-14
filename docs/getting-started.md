# Getting Started

## Installation

### Dependencies

This library is available via JitPack.

**Gradle (Kotlin):**

Add the repository to your `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}
```

Then add the dependency:

```kotlin
dependencies {
    implementation("com.github.bed-dev:stomui:Tag")
}
```

## Setup

Initialize the library in your server startup logic:

```kotlin
import codes.bed.minestom.menus.StomUI
import net.minestom.server.MinecraftServer

fun main() {
    val server = MinecraftServer.init()
    
    // Initialize StomUI
    StomUI.init(MinecraftServer.getGlobalEventHandler())
    
    server.start("0.0.0.0", 25565)
}
```

## Basic Usage

To create a simple menu:

```kotlin
import codes.bed.minestom.menus.menu
import net.minestom.server.item.Material

menu(player) {
    title("My First Menu")
    size(3) // 3 rows

    button {
        item(Material.DIAMOND) {
            displayName(Component.text("Click Me!"))
        }
        slots(13) // Center slot
        action {
            player.sendMessage("You clicked the diamond!")
        }
    }
}
```

