package com.penguin.ks7.based

import com.penguin.ks7.element.Clauses
import com.penguin.ks7.element.SymbolInstance

class ClausesBase private constructor() {
    val clausesImpl = Clauses()

    fun and(rb: RelationBase): ClausesBase {
        clausesImpl.and(rb.instantiate())
        return this
    }

    fun or(rb: RelationBase): ClausesBase {
        clausesImpl.or(rb.instantiate())
        return this
    }

    private fun start(rb: RelationBase): ClausesBase {
        clausesImpl.start(rb.instantiate())
        return this
    }

    internal fun header(rb: RelationBase): ClausesBase {
        clausesImpl.header(rb.instantiate())
        return this
    }

    internal fun header(si: SymbolInstance): ClausesBase {
        clausesImpl.header(si)
        return this
    }

    internal fun verify(): ClausesBase {
        clausesImpl.verify()
        return this
    }

    internal fun link(rb: RelationBase) {
        rb.getRelation().rule(clausesImpl)
    }

    companion object {
        fun clauses(rb: RelationBase): ClausesBase {
            return ClausesBase().start(rb)
        }

        fun headedClauses(rb: RelationBase): ClausesBase {
            return ClausesBase().header(rb)
        }
    }
}

fun RelationBase.si(): SymbolInstance {
    return SymbolInstance(getRelation())
}