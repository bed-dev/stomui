package codes.bed.minestom.menus.util

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import java.util.regex.Pattern

object Text {
    private val legacySerializer = LegacyComponentSerializer.legacySection()
    private val hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})")

    fun format(string : String?) : String = string?.let { color(it) } ?: ""

    private fun color(input : String) : String {
        // Replace & with § for legacy codes
        var text = input.replace("&", "§")

        // Handle hex colors &#RRGGBB -> §x§R§R§G§G§B§B
        val matcher = hexPattern.matcher(text)
        val buffer = StringBuffer()
        while (matcher.find()) {
            val replacement = "§x" + matcher.group(1).toCharArray().joinToString("") { "§$it" }
            matcher.appendReplacement(buffer, replacement)
        }
        matcher.appendTail(buffer)

        return legacySerializer.serialize(legacySerializer.deserialize(buffer.toString()))
    }
}

fun String.component() : net.kyori.adventure.text.Component = net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().deserialize(Text.format(this))
fun String.format() = Text.format(this)
