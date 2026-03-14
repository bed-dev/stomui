package codes.bed.minestom.menus.inventory

import net.minestom.server.event.inventory.*

data object InventoryListener : InventoryHandler {

    override fun onInventoryClick(event : InventoryClickEvent) {
    }

    override fun onInventoryClose(event : InventoryCloseEvent) {
        GuiManager.handleClose(event)
    }

    override fun onInventoryItemChange(event : InventoryItemChangeEvent) {}

    override fun onInventoryOpen(event : InventoryOpenEvent) {
        GuiManager.handleOpen(event)
    }

    override fun onInventoryPreClick(event : InventoryPreClickEvent) {
        GuiManager.handleClick(event)
    }


}
