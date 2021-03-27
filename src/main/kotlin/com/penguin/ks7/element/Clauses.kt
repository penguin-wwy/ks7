package com.penguin.ks7.element

class ClausesItem(val body: Item, val op: BuiltinOp) : Item {
    override fun _2s(): String {
        return BuiltinOp.toString(op) + body._2s()
    }

    override fun toString() = _2s()

    companion object {
        fun create(body: Item, op: BuiltinOp) = ClausesItem(body, op)
    }
}

class Clauses (private val head: SymbolInstance) : Element {
    private val body = mutableListOf<ClausesItem>()
    override fun _2s() = "$head ${body.joinToString("")}.\n"

    override fun toString(): String {
        return _2s()
    }
    
    fun start(item: Item): Clauses {
        if (body.isEmpty()) {
            body.add(ClausesItem.create(item, BuiltinOp.CONSEQUENCE))
        } else {
            throw AssertionError("No first item.")
        }
        return this
    }

    infix fun and(item: SymbolInstance): Clauses {
        body.add(ClausesItem.create(item, BuiltinOp.AND))
        return this
    }

    infix fun or(item: SymbolInstance): Clauses {
        body.add(ClausesItem.create(item, BuiltinOp.OR))
        return this
    }
}

infix fun SymbolInstance.rule(item: Item): Clauses {
    val ruleElement = Clauses(this).start(item)
    this.relation.rule(ruleElement)
    return ruleElement
}