package codes.bed.minestom.menus.bedrock

import codes.bed.minestom.menus.bedrock.form.Form
import codes.bed.minestom.menus.bedrock.form.FormManager
import net.minestom.server.entity.Player
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*

object Floodgate {

    fun sendTransfer(player : Player, address : String, port : Int) {
        val addressBytes : ByteArray = address.toByteArray(UTF_8)
        val data = ByteArray(addressBytes.size + 4)

        data[0] = (port shr 24).toByte()
        data[1] = (port shr 16).toByte()
        data[2] = (port shr 8).toByte()
        data[3] = (port).toByte()
        System.arraycopy(addressBytes, 0, data, 4, addressBytes.size)

        player.sendPluginMessage("floodgate:transfer", data)
    }

    fun closeForm(player : Player) {
        // Get the most recently opened form ID for the player
        val lastFormId = FormManager.getLastFormId(player)

        if (lastFormId != null) {
            FormManager.closeForm(player, lastFormId)
        }
    }

    fun isFloodgatePlayer(player : Player) : Boolean {
        return isFloodgateUUID(player.uuid)
    }

    fun isFloodgateUUID(uuid : UUID) : Boolean {
        return uuid.mostSignificantBits == 0L
    }

    fun sendForm(player : Player, form : Form) : Short {
        val formId : Short = FormManager.getNextFormId() // Get unique form ID
        val jsonData = form.data()?.toString()?.toByteArray(UTF_8) ?: ByteArray(0)

        val data = ByteArray(jsonData.size + 3)
        data[0] = form.type.ordinal.toByte()
        data[1] = (formId.toInt() shr 8 and 0xFF).toByte()
        data[2] = (formId.toInt() and 0xFF).toByte()

        System.arraycopy(jsonData, 0, data, 3, jsonData.size)

        player.sendPluginMessage("floodgate:form", data) // Send form to player

        FormManager.openForm(form, player, formId) // Track form in FormManager
        return formId
    }

    fun XuidToUuid(xuid : Long) : UUID {
        return UUID(0, xuid)
    }

    fun UuidToXuid(uuid : UUID) : Long {
        return uuid.leastSignificantBits
    }
}
