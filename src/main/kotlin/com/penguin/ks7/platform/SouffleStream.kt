package com.penguin.ks7.platform

import com.penguin.ks7.element.Element
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.charset.StandardCharsets.UTF_8

class SouffleStream(val ps: OutputStream) {
    val elements = mutableListOf<Element>()

    constructor(filePath: String) : this(FileOutputStream(filePath))

    fun append(e: Element) {
        elements.add(e)
    }

    fun flush() {
        ps.write(elements.map { it._2s() }.joinToString("\n").toByteArray(UTF_8))
    }
}