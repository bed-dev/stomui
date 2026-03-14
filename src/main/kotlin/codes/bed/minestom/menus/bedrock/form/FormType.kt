package codes.bed.minestom.menus.bedrock.form

import com.google.gson.annotations.SerializedName


/**
 * An enum containing the valid form types. Valid form types are:
 *
 *
 *  * [Simple Form][SimpleForm]
 *  * [Modal Form][ModalForm]
 *  * [Custom Form][CustomForm]
 *
 *
 * For more information and for code examples look at [the wiki](https://github.com/GeyserMC/Cumulus/wiki).
 *
 * @since 1.1
 */
enum class FormType {
    @SerializedName("form")
    SIMPLE_FORM,

    @SerializedName("modal")
    MODAL_FORM,

    @SerializedName("custom_form")
    CUSTOM_FORM;

    companion object {
        private val VALUES = entries.toTypedArray()

        fun fromId(id : Int) : FormType? {
            return if (id < VALUES.size) VALUES[id] else null
        }
    }
}
