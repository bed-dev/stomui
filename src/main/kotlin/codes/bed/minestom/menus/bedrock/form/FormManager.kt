package codes.bed.minestom.menus.bedrock.form

import net.minestom.server.entity.Player
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

object FormManager {

    private val activeForms = ConcurrentHashMap<Player, MutableMap<Short, Form>>() // Track active forms per player
    private val systemClosedForms =
        ConcurrentHashMap<Player, MutableSet<Short>>() // Track system-closed form IDs per player
    private val formIdGenerator = AtomicInteger(0)

    fun getNextFormId() : Short {
        val nextId = formIdGenerator.getAndUpdate { id ->
            if (id >= Short.MAX_VALUE) 0 else id + 1
        }
        return nextId.toShort()
    }

    fun getActiveForm(player : Player, formId : Short) : Form? {
        return activeForms[player]?.get(formId)
    }

    fun openForm(form : Form, player : Player, formId : Short) {
        activeForms.computeIfAbsent(player) { ConcurrentHashMap() }[formId] = form
    }

    fun getLastFormId(player : Player) : Short? {
        return activeForms[player]?.keys?.maxOrNull() // Get the highest form ID (last opened)
    }

    fun closeForm(player : Player, formId : Short) {
        player.sendPluginMessage("floodgate:form", ByteArray(0))

        // Track system-initiated close for this specific form ID
        systemClosedForms.computeIfAbsent(player) { mutableSetOf() }.add(formId)
    }

    fun handleClick(response : Pair<Int, String>, player : Player) {
        val formId = response.first.toShort()
        getActiveForm(player, formId)?.let { form ->
            when (form.type) {
                FormType.SIMPLE_FORM -> form.handleResponse(FormResponse.Simple(response.first, response.second))
                FormType.MODAL_FORM -> form.handleResponse(FormResponse.Modal(response.first, response.second))
                FormType.CUSTOM_FORM -> form.handleResponse(FormResponse.Custom(response.first, response.second))
            }
        }
    }

    fun handleClose(response : Pair<Int, String>, player : Player) {
        val formId = response.first.toShort()

        // If this form was manually closed by the system, ignore it
        systemClosedForms[player]?.let { closedForms ->
            if (closedForms.remove(formId)) {
                activeForms[player]?.remove(formId) // Remove the form
                return
            }
        }

        // If the form was closed by the player, process it normally
        activeForms[player]?.remove(formId)?.handleClose(FormResponse.Empty(response.first, response.second))
    }

    fun clear() {
        activeForms.clear()
        systemClosedForms.clear()
    }
}
