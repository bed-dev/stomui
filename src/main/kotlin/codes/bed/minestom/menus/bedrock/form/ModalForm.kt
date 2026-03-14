package codes.bed.minestom.menus.bedrock.form

import com.google.gson.JsonObject

interface ModalForm : Form {
    override val type : FormType
        get() = FormType.MODAL_FORM

    fun content() : String
    fun button1() : String
    fun button2() : String

    companion object {
        fun builder() : Builder {
            return Builder()
        }
    }

    class Builder : FormBuilder<Builder, ModalForm> {
        private var title : String = ""
        private var content : String = ""
        private var button1 : String = ""
        private var button2 : String = ""
        private var onCloseAction : () -> Unit = {}

        fun onClose(action : () -> Unit) : Builder = apply { onCloseAction = action }

        override fun title(title : String) : Builder {
            this.title = title
            return this
        }

        fun content(content : String) : Builder {
            this.content = content
            return this
        }

        fun button1(text : String) : Builder {
            this.button1 = text
            return this
        }

        fun button2(text : String) : Builder {
            this.button2 = text
            return this
        }

        override fun build() : ModalForm {
            return object : ModalForm {
                override fun content() : String = content
                override fun button1() : String = button1
                override fun button2() : String = button2
                override fun title() : String = title
                override fun type() : FormType? {
                    return FormType.MODAL_FORM
                }

                override fun handleResponse(response : FormResponse) {
                    TODO("Not yet implemented")
                }

                override fun handleClose(response : FormResponse?) {
                    onCloseAction()
                }
            }
        }
    }

    override fun data() : JsonObject {
        val json = JsonObject()
        json.addProperty("type", "modal")
        json.addProperty("title", title())
        json.addProperty("content", content())
        json.addProperty("button1", button1())
        json.addProperty("button2", button2())
        return json
    }

}
