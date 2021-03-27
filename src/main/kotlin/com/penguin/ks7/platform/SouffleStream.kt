package com.penguin.ks7.platform

import com.penguin.ks7.element.Module
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.charset.StandardCharsets.UTF_8

class SouffleStream(val ps: OutputStream) {
    val module = Module()

    constructor(filePath: String) : this(FileOutputStream(filePath))

    fun flush() {
        ps.write(module.toBytes(UTF_8))
    }
}