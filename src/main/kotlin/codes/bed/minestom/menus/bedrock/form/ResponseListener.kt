package codes.bed.minestom.menus.bedrock.form

import codes.bed.minestom.menus.util.listener
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerPluginMessageEvent
import kotlin.text.Charsets.UTF_8

object FormResponseListener {

    fun register(node : EventNode<Event>) {
        node.listener(::onPluginMessage)
    }

    private fun getFormId(data : ByteArray) : Short {
        return ((data[0].toInt() and 0xFF) shl 8 or (data[1].toInt() and 0xFF)).toShort()
    }

    private fun onPluginMessage(event : PlayerPluginMessageEvent) {
        val player = event.player
        val channel = event.identifier

        if (channel.startsWith("floodgate:form")) {
            val data = event.message
            val formId = getFormId(data)
            val response = String(data, 2, data.size - 2, UTF_8).trim()

            if (response.isBlank()) {
                FormManager.handleClose(Pair(formId.toInt(), response), player)
            } else {
                FormManager.handleClick(Pair(formId.toInt(), response), player)
            }
        }
    }
}
