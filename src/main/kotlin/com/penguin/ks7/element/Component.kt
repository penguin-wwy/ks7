package com.penguin.ks7.element

class Component(val name: String) : Element {
    private val relations = mutableListOf<Relation>()

    fun space(defFunc: (Component) -> Unit): Component {
        defFunc(this)
        return this
    }

    infix fun rel(rel: Relation): Component {
        relations.add(rel)
        return this
    }

    private fun rel2s(): String {
        return if (relations.isEmpty()) "" else
            "\n" + relations.map { it._2s("\t") }.joinToString("\n")
    }

    override fun _2s(): String {
        return ".comp $name {${rel2s()}}\n"
    }
}