package codes.bed.minestom.menus

import codes.bed.minestom.menus.bedrock.form.FormResponseListener
import codes.bed.minestom.menus.inventory.InventoryListener
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode

object StomUI {
    fun init(node : EventNode<Event>) {
        InventoryListener.register(node)
        FormResponseListener.register(node)
    }
}
