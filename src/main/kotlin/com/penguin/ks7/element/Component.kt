package com.penguin.ks7.element

class Component(val name: String) : Element {
    private val relations = mutableListOf<Relation>()
    private val namedRelation = mutableMapOf<String, Relation>()

    fun space(defFunc: (Component) -> Unit): Component {
        defFunc(this)
        return this
    }

    infix fun rel(name: String): Relation {
        return rel(Relation(name))
    }

    infix fun rel(rel: Relation): Relation {
        namedRelation[rel.name] = rel
        relations.add(rel)
        return rel
    }

    fun use(name: String): Relation {
        return namedRelation[name] ?: throw AssertionError("No such relation: $name")
    }

    private fun rel2s(): String {
        return if (relations.isEmpty()) "" else
            "\n" + relations.map { it._2s("\t") }.joinToString("\n")
    }

    override fun _2s(): String {
        return ".comp $name {${rel2s()}}\n"
    }
}