package codes.bed.minestom.menus.inventory

import codes.bed.minestom.menus.util.format
import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import net.minestom.server.event.inventory.InventoryCloseEvent
import net.minestom.server.event.inventory.InventoryOpenEvent
import net.minestom.server.event.inventory.InventoryPreClickEvent
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

class InventoryGUI(type: InventoryType, title: String) {

    val inventory = Inventory(type, Component.text(title.format()))
    private val buttons = mutableListOf<Button>()
    var filler = false
    var onClose : ((Player) -> Unit)? = null // Properly settable `onClose` action

    fun addButton(button: Button): InventoryGUI {
        buttons.add(button)
        return this
    }

    fun decorate(player: Player) {
        buttons.forEach { button ->
            button.item?.let {
                button.slots.forEach { slot ->
                    inventory.setItemStack(slot, it)
                }
            }
        }

        if (filler) {
            for (i in 0 until inventory.size) {
                if (inventory.getItemStack(i).isAir) {
                    inventory.setItemStack(
                        i,
                        ItemStack.builder(Material.GRAY_STAINED_GLASS_PANE).customName(Component.text(" ")).build()
                    )
                }
            }
        }
    }

    fun filler() = apply { this.filler = true }

    fun onClick(event: InventoryPreClickEvent) {
        event.isCancelled = true
        buttons.find { it.slots.contains(event.slot) }?.click(event)
    }

    fun onOpen(event: InventoryOpenEvent) {
        decorate(event.player)
    }

    fun onClose(event: InventoryCloseEvent) {
        onClose?.invoke(event.player)
    }

    fun open(player: Player) {
        decorate(player) // Ensure the inventory is decorated before opening
        GuiManager.openGUI(this, player)
    }
}
