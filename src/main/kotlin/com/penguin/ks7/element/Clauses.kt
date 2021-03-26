package com.penguin.ks7.element

//class Item(private val name: String) : Element {
//    override fun _2s() = name
//
//    override fun toString() = _2s()
//
//    companion object {
//        fun create(s: String): Item {
//            return Item(s)
//        }
//    }
//}

class ClausesItem(val owner: Relation, vararg s: Item) : Element {
    private val items: Array<out Item> = s
    private var negated = false
    private lateinit var beInstance: Instance

    constructor(owner: Relation, vararg s: String) : this(owner, *s.toList().map { Item.variable(it) }.toTypedArray())

    fun negation(): ClausesItem {
        negated = true
        return this
    }

    fun instantiated(beInstance: Instance): ClausesItem {
        this.beInstance = beInstance
        return this
    }

    private fun instantiated(): String {
        return if (this::beInstance.isInitialized) beInstance.prefix() else ""
    }

    override fun _2s() = "${if (negated) "!" else ""}${instantiated()}${owner.name}(${items.joinToString()})"

    override fun toString(): String {
        return _2s()
    }
}

fun Relation.item(vararg items: Item): ClausesItem {
    return ClausesItem(this, *items)
}

fun Relation.item(vararg s: String): ClausesItem {
    return ClausesItem(this, *s)
}

class Clauses (private val head: ClausesItem) : Element {
    private val body = mutableListOf<Element>()
    override fun _2s() = "$head :- ${body.joinToString(" ")}.\n"

    override fun toString(): String {
        return _2s()
    }
    
    fun start(item: ClausesItem): Clauses {
        if (body.isEmpty()) {
            body.add(item)
        } else {
            throw AssertionError("No first item.")
        }
        return this
    }

    infix fun and(item: ClausesItem): Clauses {
        body.add(And)
        body.add(item)
        return this
    }

    infix fun or(item: ClausesItem): Clauses {
        body.add(Or)
        body.add(item)
        return this
    }
}

infix fun ClausesItem.rule(item: ClausesItem): Clauses {
    val ruleElement = Clauses(this).start(item)
    this.owner.rule(ruleElement)
    return ruleElement
}