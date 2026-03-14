# Optimization & Architecture

The library is designed to be lightweight and modular.

## Optimization Tips

- **Avoid Heavy Logic in Animation**: The `animate` block runs every tick (or specified interval). Keep it lightweight.
- **Reuse Menus**: You can define a `Menu` instance once and `open(player)` it for multiple players (stateless) or create per-player instances (stateful).
- **Packet Handling**: `refresh` tries to minimize packet spam by only updating changed items.

## Architecture

- **Menu**: The high-level DSL aggregator.
- **InventoryGUI**: Handles Minestom `Inventory` interactions.
- **Form**: Handles Bedrock packet construction and sending.

