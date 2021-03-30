package com.penguin.ks7.based

import com.penguin.ks7.element.Clauses
import com.penguin.ks7.element.SymbolInstance

class ClausesBase private constructor(head: SymbolInstance) {
    val clausesImpl = Clauses(head)

    fun and(rb: RelationBase): ClausesBase {
        return this
    }

    fun or(rb: RelationBase): ClausesBase {
        return this
    }

    companion object {
        fun clauses(rb: RelationBase): ClausesBase {
            return ClausesBase(rb.si())
        }
    }
}

fun RelationBase.si(): SymbolInstance {
    return SymbolInstance(getRelation())
}