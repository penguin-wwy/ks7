package com.penguin.ks7.element

interface Instance : Element {
    fun prefix() = ""
}

class RelationInstance(val relation: Relation) : Instance {

    val items = mutableListOf<Item>()

    fun addItems(vararg its: Item): RelationInstance {
        items.addAll(its)
        return this
    }

    override fun _2s(): String {
        return "${relation.name}(${items.joinToString()}).\n"
    }
}

class CompInstance(val name: String, val comp: Component) : Instance {
    override fun _2s(): String {
        return ".init $name = ${comp.name}\n"
    }

    override fun prefix() = "$name."

    fun rel(relName: String, vararg args: String): ClausesItem {
        return comp.use(relName).item(*args).instantiated(this)
    }
}

infix fun Component.init(name: String): CompInstance {
    return CompInstance(name, this)
}