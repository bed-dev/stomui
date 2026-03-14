package codes.bed.minestom.menus.bedrock.form

import com.google.gson.JsonArray
import com.google.gson.JsonObject

interface CustomForm : Form {
    override val type : FormType
        get() = FormType.CUSTOM_FORM

    fun components() : List<Component>

    companion object {
        fun builder() : Builder {
            return Builder()
        }
    }

    class Builder : FormBuilder<Builder, CustomForm> {
        private var title : String = ""
        private val components : MutableList<Component> = mutableListOf()

        private var onCloseAction : () -> Unit = {}

        fun onClose(action : () -> Unit) : Builder =
            apply { onCloseAction = action }

        override fun title(title : String) : Builder {
            this.title = title
            return this
        }

        fun dropdown(text : String, options : List<String>, default : Int = 0) : Builder {
            components.add(DropdownComponent(text, options, default))
            return this
        }

        fun input(text : String, placeholder : String = "", default : String = "") : Builder {
            components.add(InputComponent(text, placeholder, default))
            return this
        }

        fun toggle(text : String, default : Boolean = false) : Builder {
            components.add(ToggleComponent(text, default))
            return this
        }

        fun slider(text : String, min : Float, max : Float, step : Float = 1f, default : Float = min) : Builder {
            components.add(SliderComponent(text, min, max, step, default))
            return this
        }

        override fun build() : CustomForm {
            return object : CustomForm {

                override fun title() : String = title
                override fun components() : List<Component> = components
                override fun type() : FormType = FormType.CUSTOM_FORM

                override fun handleResponse(response : FormResponse) {
                    if (response !is FormResponse.Custom) return

                    components.forEachIndexed { index, component ->
                        when (component) {
                            is DropdownComponent -> {
                                val response = response.responses[index] as Int
                                // Do something with the response
                            }

                            is InputComponent -> {
                                val response = response.responses[index] as String
                                // Do something with the response
                            }

                            is ToggleComponent -> {
                                val response = response.responses[index] as Boolean
                                // Do something with the response
                            }

                            is SliderComponent -> {
                                val response = response.responses[index] as Float
                                // Do something with the response
                            }
                        }
                    }
                }

                override fun handleClose(response : FormResponse?) {
                    onCloseAction()
                }
            }
        }
    }

    interface Component {
        fun build() : JsonObject
    }

    class DropdownComponent(
        private val text : String,
        private val options : List<String>,
        private val default : Int
    ) : Component {
        override fun build() : JsonObject {
            val json = JsonObject()
            json.addProperty("type", "dropdown")
            json.addProperty("text", text)
            json.addProperty("default", default)
            val optionsArray = JsonArray()
            options.forEach { optionsArray.add(it) }
            json.add("options", optionsArray)
            return json
        }
    }

    class InputComponent(
        private val text : String,
        private val placeholder : String,
        private val default : String
    ) : Component {
        override fun build() : JsonObject {
            val json = JsonObject()
            json.addProperty("type", "input")
            json.addProperty("text", text)
            json.addProperty("placeholder", placeholder)
            json.addProperty("default", default)
            return json
        }
    }

    class ToggleComponent(
        private val text : String,
        private val default : Boolean
    ) : Component {
        override fun build() : JsonObject {
            val json = JsonObject()
            json.addProperty("type", "toggle")
            json.addProperty("text", text)
            json.addProperty("default", default)
            return json
        }
    }

    class SliderComponent(
        private val text : String,
        private val min : Float,
        private val max : Float,
        private val step : Float,
        private val default : Float
    ) : Component {
        override fun build() : JsonObject {
            val json = JsonObject()
            json.addProperty("type", "slider")
            json.addProperty("text", text)
            json.addProperty("min", min)
            json.addProperty("max", max)
            json.addProperty("step", step)
            json.addProperty("default", default)
            return json
        }
    }

    override fun data() : JsonObject {
        val json = JsonObject()
        json.addProperty("type", "custom_form")
        json.addProperty("title", title())

        val contentArray = JsonArray()
        components().forEach { contentArray.add(it.build()) }

        json.add("content", contentArray)
        return json
    }

}
