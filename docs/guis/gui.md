# Inventory GUI

This library allows you to create simple Inventory GUIs that work for Java clients and are automatically converted to
Forms for Bedrock clients if needed.

## Creating a Menu

```kotlin
val myMenu = menu {
    title("My Super Menu")
    size(InventoryType.CHEST_3_ROW)

    button {
        slots(11) // Middle slot in 3 row chest
        title("&cClick Me!")
        text("Bedrock Form Button Text")
        item(Material.DIAMOND) {
            lore("A cool diamond")
        }
        action {
            // Your code here
            player.sendMessage("You clicked the diamond!")
        }
    }
}
```

## Opening a Menu

```kotlin
myMenu.open(player)
```

## Animations

You can animate the menu contents.

```kotlin
myMenu.animate(player, 1L) { tick ->
    // Update logic here
    // this.buttons.clear() ...
    // or use refresh
}
```

