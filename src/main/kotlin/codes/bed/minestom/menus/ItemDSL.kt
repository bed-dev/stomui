package codes.bed.minestom.menus

import codes.bed.minestom.menus.util.format
import codes.bed.minestom.menus.util.component
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

// Extension for ItemStack.Builder to support String for display name
@MenuDSL
fun ItemStack.Builder.displayName(name: String) = this.customName(name.format().component())

// Extension for ItemStack.Builder to support String varargs for lore
@MenuDSL
fun ItemStack.Builder.lore(vararg lore: String) = this.lore(lore.map { it.format().component() })

// Extension for ItemStack.Builder to support List<String> for lore
@MenuDSL
@JvmName("loreList")
fun ItemStack.Builder.lore(lore: List<String>) = this.lore(lore.map { it.format().component() })

// Convenience to set material
@MenuDSL
fun ItemStack.Builder.material(material: Material) = this.material(material)

// Convenience for specific amount
@MenuDSL
fun ItemStack.Builder.amount(amount: Int) = this.amount(amount)
