package com.penguin.ks7.element

class Component(val name: String) : Element, ElementGraph {
    override val elements = mutableListOf<Element>()
    private val namedRelation = mutableMapOf<String, Relation>()

    fun space(defFunc: Component.() -> Unit): Component {
        defFunc(this)
        return this
    }

    infix fun rel(name: String): Relation {
        return rel(Relation(name))
    }

    infix fun rel(rel: Relation): Relation {
        namedRelation[rel.name] = super.register(rel)
        return rel
    }

    fun use(name: String): Relation {
        return namedRelation[name] ?: throw AssertionError("No such relation: $name")
    }

    override fun <T : Element> register(element: T): T {
        return when (element) {
            is Relation -> rel(element) as T
            is Instance, is Clauses -> super.register(element)
            else -> element
        }
    }

    override fun _2s(): String {
        return ".comp $name {\n${joinToString(prefix = "\t")}}\n"
    }
}