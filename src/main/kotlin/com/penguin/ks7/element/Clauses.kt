package com.penguin.ks7.element

class Clauses (private val head: SymbolInstance) : Element {
    private val body = mutableListOf<Element>()
    override fun _2s() = "$head :- ${body.joinToString(" ")}.\n"

    override fun toString(): String {
        return _2s()
    }
    
    fun start(item: SymbolInstance): Clauses {
        if (body.isEmpty()) {
            body.add(item)
        } else {
            throw AssertionError("No first item.")
        }
        return this
    }

    infix fun and(item: SymbolInstance): Clauses {
        body.add(And)
        body.add(item)
        return this
    }

    infix fun or(item: SymbolInstance): Clauses {
        body.add(Or)
        body.add(item)
        return this
    }
}

infix fun SymbolInstance.rule(item: SymbolInstance): Clauses {
    val ruleElement = Clauses(this).start(item)
    this.relation.rule(ruleElement)
    return ruleElement
}