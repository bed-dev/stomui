package codes.bed.minestom.menus.bedrock.form

import com.google.gson.JsonArray
import com.google.gson.JsonObject

interface SimpleForm : Form {
    fun content() : String
    fun buttons() : List<Button>

    override val type : FormType
        get() = FormType.SIMPLE_FORM

    companion object {
        fun builder() : Builder = Builder()
    }

    class Builder : FormBuilder<Builder, SimpleForm> {
        private var title : String = ""
        private var content : String = ""
        private val buttons = mutableListOf<Button>()
        private var onCloseAction : () -> Unit = {}

        fun onClose(action : () -> Unit) : Builder = apply { onCloseAction = action }

        override fun title(title : String) : Builder = apply { this.title = title }

        fun content(content : String) : Builder = apply { this.content = content }
        fun button(text : String, image : String? = null) : Builder = apply { buttons.add(Button(text, image)) }
        fun button(button : Button) : Builder = apply { buttons.add(button) }


        override fun build() : SimpleForm {
            return object : SimpleForm {
                override fun title() : String = this@Builder.title
                override fun content() : String = this@Builder.content
                override fun buttons() : List<Button> = this@Builder.buttons
                override fun type() : FormType = FormType.SIMPLE_FORM

                override fun handleResponse(response : FormResponse) {
                    if (response !is FormResponse.Simple) return

                    buttons[response.buttonId].actions.forEach { it() }
                }

                override fun handleClose(response : FormResponse?) {
                    onCloseAction()
                }
            }
        }
    }

    class Button(
        private val text : String,
        private val image : String? = null
    ) {
        val actions : MutableList<() -> Unit> = mutableListOf()

        fun action(action : () -> Unit) = apply { actions.add(action) }

        fun toJson() : JsonObject {
            return JsonObject().apply {
                addProperty("text", text)

                image?.let {
                    add("image", JsonObject().apply {
                        addProperty("type", "url")
                        addProperty("data", it)
                    })
                }
            }
        }
    }

    override fun data() : JsonObject {
        return JsonObject().apply {
            addProperty("type", "form")
            addProperty("title", title())
            addProperty("content", content())
            add("buttons", JsonArray().apply {
                buttons().forEach { add(it.toJson()) }
            })
        }
    }

}
