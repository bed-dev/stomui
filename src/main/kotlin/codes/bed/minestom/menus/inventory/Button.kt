package codes.bed.minestom.menus.inventory

import net.minestom.server.event.inventory.InventoryPreClickEvent
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import java.util.function.Consumer

class Button(
    var slots : IntArray = intArrayOf(0),
    var item : ItemStack? = ItemStack.of(Material.BARRIER),
    private var actions : MutableList<Consumer<InventoryPreClickEvent>> = mutableListOf(),
) {
    fun action(action: Consumer<InventoryPreClickEvent>) = apply { actions.add(action) }

    fun item(item: ItemStack): Button {
        this.item = item;

        return this
    }

    fun click(event: InventoryPreClickEvent): Boolean {
        actions.forEach { it.accept(event) }
        return true
    }

    fun slots(vararg slots : Int) : Button {
        this.slots = slots
        return this
    }

}


