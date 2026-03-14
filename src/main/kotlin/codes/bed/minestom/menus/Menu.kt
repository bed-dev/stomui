package codes.bed.minestom.menus

import codes.bed.minestom.menus.bedrock.Floodgate
import codes.bed.minestom.menus.bedrock.form.SimpleForm
import codes.bed.minestom.menus.inventory.GuiManager
import codes.bed.minestom.menus.inventory.InventoryGUI
import codes.bed.minestom.menus.util.component
import codes.bed.minestom.menus.util.format
import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.timer.Task
import net.minestom.server.timer.TaskSchedule

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
annotation class MenuDSL

@MenuDSL
class Menu {
    var title : String = ""
    var size : InventoryType = InventoryType.CHEST_3_ROW
    val buttons : MutableList<Button> = mutableListOf()
    val optionalButtons : MutableMap<String, Button> = mutableMapOf()
    var filler = false

    var action : (Menu.() -> Unit)? = null
    private var animationTask : Task? = null // Stores running animation

    fun title(title : String) = apply { this.title = title }

    fun size(size : InventoryType) = apply { this.size = size }

    fun size(size : Int) = apply {
        val rows = (size / 9).coerceIn(1, 6)
        this.size = InventoryType.valueOf("CHEST_${rows}_ROW")
    }

    fun button(block : Button.() -> Unit) = apply {
        buttons.add(Button(this).apply(block))
    }

    fun optionalButton(id : String, block : Button.() -> Unit) = apply {
        optionalButtons[id] = Button(this).apply(block)
    }

    fun filler() = apply { this.filler = true }

    fun action(block : Menu.() -> Unit) = apply {
        this.action = block
    }

    fun animate(player : Player, intervalSeconds : Long, block : Menu.(tick : Int) -> Unit) = apply {
        stopAnimation() // Cancel any running animation
        var tick = 0

        animationTask = MinecraftServer.getSchedulerManager()
            .scheduleTask({
                this.block(tick++)
                refresh(player) // Dynamically refresh contents
            }, TaskSchedule.seconds(0), TaskSchedule.seconds(intervalSeconds))
    }

    fun stopAnimation() {
        animationTask?.cancel()
        animationTask = null
    }

    fun buildForForm() : SimpleForm {
        return SimpleForm.Builder()
            .title(title)
            .content(title)
            .apply {
                buttons.forEach { button -> button.buildForForm()?.let { button(it) } }
            }
            .onClose {
                stopAnimation()
            }
            .build()
    }

    fun buildForInventory() : InventoryGUI {
        return InventoryGUI(size, title).apply {
            buttons.forEach { addButton(it.buildForInventory()) }
            if (this@Menu.filler) filler()

            onClose = { stopAnimation() }
        }
    }

    fun open(player : Player) {
        action?.invoke(this) // Run action before opening

        if (Floodgate.isFloodgatePlayer(player)) {
            buildForForm().send(player)
        } else {
            buildForInventory().open(player)
        }

        // Start animation if one is scheduled
        animationTask?.unpark()
    }

    fun clearButtons() {
        buttons.clear()
        optionalButtons.clear()
    }

    /**
     * Refreshes the menu for the player.
     * @param player The player to refresh for.
     * @param clear If true, clears existing buttons before applying the block. Defaults to false (append mode).
     * @param block The block to apply to the menu.
     */
    fun refresh(player : Player, clear: Boolean = false, block : Menu.() -> Unit = {}) {
        // Apply updates to the existing menu instance
        if (clear) clearButtons()
        this.apply(block)

        if (Floodgate.isFloodgatePlayer(player)) {
            Floodgate.closeForm(player)
            buildForForm().send(player)  // Reopen with updated contents
        } else {
            val inventoryGUI = GuiManager.getGUI(player) ?: return // Ensure inventory GUI exists

            // Don't reconstruct Component unless title changed
            if (inventoryGUI.title != Component.text(title)) {
                 inventoryGUI.title = Component.text(title)
            }

            buttons.forEach { button ->
                button.slots.forEach { slot ->
                    // Only update if item is different to avoid packet spam if possible
                    // But Minestom generally handles this well.
                    // For now, simple set is fine.
                    inventoryGUI.setItemStack(slot, button.item)
                }
            }

            // Allow manual update if needed, but usually not required for server side inventory modification view
            // player.inventory.update()
        }
    }


    fun MutableList<Button>.addButton(button : String) {
        optionalButtons[button]?.let { buttons.add(it) }
    }
}

fun menu(block : Menu.() -> Unit) : Menu = Menu().apply(block)
fun menu(player : Player, block : Menu.() -> Unit) = Menu().apply(block).open(player)
