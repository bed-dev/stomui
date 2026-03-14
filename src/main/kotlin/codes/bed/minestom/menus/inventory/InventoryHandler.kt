package codes.bed.minestom.menus.inventory

import codes.bed.minestom.menus.util.listener
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.inventory.*

sealed interface InventoryHandler {
    fun register(node: EventNode<Event>) {
        node.listener(::onInventoryClick)
        node.listener(::onInventoryClose)
        node.listener(::onInventoryItemChange)
        node.listener(::onInventoryOpen)
        node.listener(::onInventoryPreClick)
    }

    fun onInventoryClick(event: InventoryClickEvent)
    fun onInventoryClose(event: InventoryCloseEvent)
    fun onInventoryItemChange(event: InventoryItemChangeEvent)
    fun onInventoryOpen(event: InventoryOpenEvent)
    fun onInventoryPreClick(event: InventoryPreClickEvent)
}
