package codes.bed.minestom.menus.bedrock.form

import com.google.gson.JsonParser

interface FormResponse {
    val formId : Int
    val data : String

    class Empty(override val formId : Int, override val data : String) : FormResponse {}

    class Simple(override val formId : Int, override val data : String) : FormResponse {
        val buttonId : Int = data.toInt()
    }

    class Modal(override val formId : Int, override val data : String) : FormResponse {
        val buttonId : Int = data.toInt()
    }

    class Custom(override val formId : Int, override val data : String) : FormResponse {
        val responses : List<Any> = JsonParser.parseString(data).asJsonArray.asList()
    }
}
