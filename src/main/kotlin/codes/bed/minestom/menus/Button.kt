package codes.bed.minestom.menus

import codes.bed.minestom.menus.bedrock.form.SimpleForm
import codes.bed.minestom.menus.inventory.Button as InventoryButton
import codes.bed.minestom.menus.util.component
import codes.bed.minestom.menus.util.format
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

@MenuDSL
class Button(private val menu: Menu) {
    var title: String = ""
    var action: () -> Unit = {}
    var lore: List<String> = listOf()
    var text: String = title
    var image: String? = null
    var slots: IntArray = intArrayOf(0)
    var onlyJava: Boolean = false
    var item: ItemStack = ItemStack.AIR

    fun title(title: String) = apply { this.title = title }

    fun action(action: () -> Unit) = apply { this.action = action }

    fun lore(vararg lore: String) = apply { this.lore = lore.toList() }

    fun text(text: String) = apply { this.text = text }

    fun image(image: String?) = apply { this.image = image }

    fun slots(vararg slots: Int) = apply { this.slots = slots }

    @MenuDSL
    inline fun item(
        material: Material = Material.STONE,
        block: @MenuDSL ItemStack.Builder.() -> Unit
    ) = apply {
        this.item = ItemStack.builder(material).apply(block).build()
    }

    fun onlyJava(onlyJava: Boolean) = apply { this.onlyJava = onlyJava }

    fun refresh(player: Player, block: Menu.() -> Unit) {
        menu.refresh(player, false, block)
    }

    fun buildForForm(): SimpleForm.Button? {
        if (onlyJava) return null
        return SimpleForm.Button(text, image).apply { action { action() } }
    }

    fun buildForInventory(): InventoryButton {
        return InventoryButton(
            slots,
            item.builder().customName(title.format().component()).lore(lore.map { it.format().component() }).build()
        ).apply { action { action() } }
    }
}

