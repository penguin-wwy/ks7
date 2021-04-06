package com.penguin.ks7.element

interface Instance : Item {
    fun owner(owner: Instance) = this

    fun prefix() = ""
}

open class RelationInstance(val relation: Relation) : Instance {

    private lateinit var beInstance: Instance
    open val items = mutableListOf<Item>()

    fun addItems(vararg its: Item): RelationInstance {
        items.addAll(its)
        return this
    }

    override fun owner(owner: Instance): RelationInstance {
        beInstance = owner
        return this
    }

    private fun owner(): String {
        return if (this::beInstance.isInitialized) beInstance.prefix() else ""
    }

    override fun _2s(): String {
        return "${owner()}${relation.name}(${items.joinToString()}).\n"
    }
}

class SymbolInstance(relation: Relation) : RelationInstance(relation) {
    private var negated = false
    private lateinit var beInstance: Instance

    constructor(relation: Relation, item: Item, vararg s: Item) : this(relation) {
        items.add(item)
        items.addAll(s)
    }

    constructor(owner: Relation, s: String, vararg ss: String) :
            this(owner, Item.variable(s), *ss.toList().map { Item.variable(it) }.toTypedArray())

    fun negation(): SymbolInstance {
        negated = true
        return this
    }

    override fun owner(beInstance: Instance): SymbolInstance {
        this.beInstance = beInstance
        return this
    }

    private fun owner(): String {
        return if (this::beInstance.isInitialized) beInstance.prefix() else ""
    }

    override fun _2s() = "${if (negated) "!" else ""}${owner()}${relation.name}(${items.joinToString()})"

    override fun toString(): String {
        return _2s()
    }
}

fun Relation.item(single: Item, vararg items: Item): SymbolInstance {
    return SymbolInstance(this, single, *items)
}

fun Relation.item(s: String, vararg ss: String): SymbolInstance {
    return SymbolInstance(this, s, *ss)
}

class CompInstance(val name: String, val comp: Component) : Instance {
    override fun _2s(): String {
        return ".init $name = ${comp.name}\n"
    }

    override fun prefix() = "$name."

    fun relItem(relName: String, s: String, vararg args: String): SymbolInstance {
        return comp.use(relName).item(s, *args).owner(this)
    }

    fun relInt(relName: String, s: Item, vararg args: Item): RelationInstance {
        return comp.use(relName).instantiate(s, *args).owner(this)
    }
}

infix fun Component.init(name: String): CompInstance {
    return CompInstance(name, this)
}

interface Instantiated {
    fun instantiate(vararg items: Item): Instance
}

class GenericInstance<T : NamedType>(val name: String, val ty: T) : Instance {
    override fun _2s(): String {
        return ".init $name = ${ty.desc()}\n"
    }
}