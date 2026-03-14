# Menu DSL

The Menu DSL provides a declarative way to build GUIs that work as both Chest Inventories (Java Edition) and Forms (Bedrock Edition via Floodgate).

## Structure

```kotlin
menu {
    title("Title")
    size(InventoryType.CHEST_3_ROW) // or size(3) for rows
    
    // Add buttons
    button {
       ...
    }
    
    // Add logic
    action {
       // Runs when opened
    }
}
```

## Buttons

Buttons can be defined with an item (for Inventory) and text/image (for Bedrock Forms).

```kotlin
button {
    // Inventory Item
    item(Material.STONE) {
        customName(Component.text("Stone"))
    }
    
    // Bedrock Form Text
    text("Bedrock Button Text")
    
    // Position in Inventory
    slots(0, 1) 
    
    // Click Action
    action {
        // Handle click
    }
}
```

