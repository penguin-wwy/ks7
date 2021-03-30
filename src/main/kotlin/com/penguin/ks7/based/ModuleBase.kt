package com.penguin.ks7.based

import com.penguin.ks7.element.Module
import java.io.FileOutputStream
import java.io.OutputStream
import kotlin.reflect.KClass

abstract class ModuleBase(val ps: OutputStream) {
    val module = Module()

    fun define(jClass: Class<out RelationBase>) {
        define(jClass.kotlin)
    }

    fun define(klass: KClass<out RelationBase>) {
        // TODO
    }

    fun finish() {
        module.elements.forEach {
            // TODO
        }
    }
}

class DefaultModule() : ModuleBase(System.out) {

}

class FileModule(fileOutStream: FileOutputStream) : ModuleBase(fileOutStream), AutoCloseable {
    constructor(filePath: String) : this(FileOutputStream(filePath))

    override fun close() {
        ps.close()
    }
}