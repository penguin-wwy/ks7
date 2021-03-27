package com.penguin.ks7.element

import java.nio.charset.Charset

class Module : ElementGraph {
    override val elements = mutableListOf<Element>()

    fun toBytes(charSet: Charset): ByteArray {
        return elements.joinToString("") { it._2s() }.toByteArray(charSet)
    }
}