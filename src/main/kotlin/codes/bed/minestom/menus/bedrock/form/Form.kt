package codes.bed.minestom.menus.bedrock.form

import codes.bed.minestom.menus.bedrock.Floodgate
import com.google.gson.JsonObject
import net.minestom.server.entity.Player
import java.io.Serializable

/**
 * Base class of all Forms. While it can be used it doesn't contain every data you could get when
 * using the specific class of the form type.
 */
interface Form : Serializable {
    val type : FormType
    var formId : Short?
        get() = null
        set(value) {}

    /**
     * Returns the title of the Form.
     */
    fun title() : String?

    /**
     * Returns a type of form
     */
    fun type() : FormType?

    /**
     * Returns a form's player
     */
//    fun player() : UUID?

    /**
     * Returns a form's data
     */
    fun data() : JsonObject?

    /**
     * Sends the form to the player
     */
    fun send(player : Player) {
        formId = Floodgate.sendForm(player, this)
    }

    /**
     * Handles the response from the player
     */
    fun handleResponse(response : FormResponse)

    /**
     * Handles the closing of the form
     */
    fun handleClose(response : FormResponse?)

}

