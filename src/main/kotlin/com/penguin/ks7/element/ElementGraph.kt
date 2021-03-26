package com.penguin.ks7.element

interface ElementGraph {
    val elements: MutableCollection<Element>

    fun <T : Element>register(element: T): T {
        elements.add(element)
        return element
    }

    fun joinToString(sep: String = "", prefix: String = "", suffix: String = ""): String {
        return elements.joinToString(sep + prefix + suffix, prefix = prefix, postfix =  suffix) { it._2s() }
    }
}