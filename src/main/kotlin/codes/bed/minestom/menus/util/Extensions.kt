package codes.bed.minestom.menus.util

import net.minestom.server.event.Event
import net.minestom.server.event.EventNode

inline fun <reified T : Event> EventNode<in T>.listener(noinline listener : T.() -> Unit) : EventNode<in T> {
    this.addListener(T::class.java, listener)

    return this
}
