package com.penguin.ks7.based

import com.penguin.ks7.annotation.Rule
import com.penguin.ks7.element.Clauses
import com.penguin.ks7.element.Module
import com.penguin.ks7.element.Relation
import com.penguin.ks7.element.into
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.charset.StandardCharsets.UTF_8
import kotlin.reflect.KClass

abstract class ModuleBase(val ps: OutputStream) {
    val module = Module()
    val rbStore = mutableSetOf<KClass<out RelationBase>>()

    fun define(jClass: Class<out RelationBase>) {
        define(jClass.kotlin)
    }

    fun define(klass: KClass<out RelationBase>) {
        rbStore.add(klass)
        GlobalModule.getRelation(klass) into module
    }

    internal fun rela(rel: Relation) {
        rel into module
    }

    internal fun rule(clauses: Clauses) {
        clauses into module
    }

    fun finish() {
        rbStore.forEach {
            val obj = it.java.getConstructor().newInstance()
            it.java.methods
                .filter { method -> method.getAnnotation(Rule::class.java) != null }
                .forEach { method -> method.invoke(obj) }
        }
        ps.write(module.toBytes(UTF_8))
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