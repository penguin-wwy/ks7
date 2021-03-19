package com.penguin.ks7.element

class Item(private val name: String) : Element {
    override fun _2s() = name

    override fun toString() = _2s()

    companion object {
        fun create(s: String): Item {
            return Item(s)
        }
    }
}

class ClausesItem(val owner: Relation, vararg s: Item) : Element {
    private val items: Array<out Item> = s
    private var negated = false

    constructor(owner: Relation, vararg s: String) : this(owner, *s.toList().map { Item.create(it) }.toTypedArray())

    fun negation(): ClausesItem {
        negated = true
        return this
    }

    override fun _2s() = "${if (negated) "!" else ""}${owner.name}(${items.joinToString()})"

    override fun toString(): String {
        return _2s()
    }
}

fun Relation.item(vararg s: String): ClausesItem {
    return ClausesItem(this, *s)
}

class Clauses (private val head: ClausesItem, vararg itemArray: ClausesItem) : Element {
    private val body = itemArray.toMutableList()
    override fun _2s() = "$head :- ${body.joinToString()}."

    override fun toString(): String {
        return _2s()
    }

    infix fun and(item: ClausesItem): Clauses {
        body.add(item)
        return this
    }
}

infix fun ClausesItem.rule(item: ClausesItem): Clauses {
    val ruleElement = Clauses(this, item)
    this.owner.rule(ruleElement)
    return ruleElement
}