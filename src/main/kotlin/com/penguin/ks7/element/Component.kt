package com.penguin.ks7.element

class Component(val name: String) : Element, ElementGraph {
    override val elements = mutableListOf<Element>()
    private val namedRelation = mutableMapOf<String, Relation>()
    private fun addRelDecl(rel: Relation) {
        namedRelation[rel.name] = register(rel)
    }

    fun space(defFunc: Component.() -> Unit): Component {
        defFunc(this)
        return this
    }

    infix fun rel(name: String): Relation {
        return rel(Relation(name))
    }

    infix fun rel(rel: Relation): Relation {
        addRelDecl(rel)
        return rel
    }

    fun instantiate(name: String, vararg items: Item): Instance {
        return instantiate(use(name), *items)
    }

    fun instantiate(rel: Relation, vararg items: Item): Instance {
        val instance = rel.instantiate(*items)
        return register(instance)
    }

    fun use(name: String): Relation {
        return namedRelation[name] ?: throw AssertionError("No such relation: $name")
    }

    private fun rel2s(): String {
        val rels = elements.filterIsInstance<Relation>()
        return if (rels.isEmpty()) "" else
            "\n" + rels.map { it._2s("\t") }.joinToString("\n")
    }

    override fun _2s(): String {
        return ".comp $name {\n${joinToString(prefix = "\t")}}\n"
    }
}