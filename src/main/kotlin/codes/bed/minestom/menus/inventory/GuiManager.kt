package codes.bed.minestom.menus.inventory

import net.minestom.server.entity.Player
import net.minestom.server.event.inventory.InventoryCloseEvent
import net.minestom.server.event.inventory.InventoryOpenEvent
import net.minestom.server.event.inventory.InventoryPreClickEvent
import net.minestom.server.inventory.AbstractInventory
import net.minestom.server.inventory.Inventory

object GuiManager {
    private val activeInventories = mutableMapOf<AbstractInventory, InventoryGUI>()

    fun openGUI(gui: InventoryGUI, player: Player) {
        registerHandledInventory(gui.inventory, gui)
        player.openInventory(gui.inventory)
    }

    fun getGUI(player : Player) : Inventory? {
        val openInv = player.openInventory ?: return null
        if (!activeInventories.containsKey(openInv)) return null
        return openInv as? Inventory
    }

    private fun registerHandledInventory(inventory: AbstractInventory, handler: InventoryGUI) {
        activeInventories[inventory] = handler
    }

    private fun unregisterInventory(inventory : AbstractInventory) {
        activeInventories.remove(inventory)
    }

    fun handleClick(event: InventoryPreClickEvent) {
        activeInventories[event.inventory]?.onClick(event)
    }

    fun handleOpen(event: InventoryOpenEvent) {
        activeInventories[event.inventory]?.onOpen(event)
    }

    fun handleClose(event: InventoryCloseEvent) {
        activeInventories[event.inventory]?.onClose(event)

        event.inventory?.let {
            unregisterInventory(it)
        }
    }

    fun clear() = activeInventories.clear()
}
