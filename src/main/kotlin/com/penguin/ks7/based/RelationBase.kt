package com.penguin.ks7.based

import com.penguin.ks7.annotation.Name
import com.penguin.ks7.based.tools.AttributeFactory
import com.penguin.ks7.element.*
import kotlin.reflect.KClass

abstract class RelationBase {
    internal val inputs = mutableListOf<Item>()

    fun variable(s: String): RelationBase {
        inputs.add(Item.variable(s))
        return this
    }

    fun integer(i: Int): RelationBase {
        inputs.add(Item.integer(i))
        return this
    }

    fun symbol(s: String): RelationBase {
        inputs.add(Item.symbol(s))
        return this
    }

    fun addItems(ss: Array<out Item>) {
        inputs.addAll(ss)
    }

    fun and(rb: RelationBase): ClausesBase {
        return ClausesBase.clauses(this).and(rb)
    }

    fun assertTrue(s0: VariableItem, s1: VariableItem, rb: RelationBase) {
        assertTrue(s0, s1, ClausesBase.clauses(rb))
    }

    fun assertTrue(s0: VariableItem, s1: VariableItem, clause: ClausesBase) {
        clause.header(SymbolInstance(getRelation()).addItems(s0, s1) as SymbolInstance)
        clause.verify().link(this)
        GlobalModule.add(clause)
    }

    internal fun instantiate(): SymbolInstance {
        return SymbolInstance(getRelation()).addItems(*inputs.toTypedArray()) as SymbolInstance
    }

    internal fun getRelation(): Relation {
        return GlobalModule.getRelation(this::class)
    }

    companion object {
        internal fun toRelation(rbClass: KClass<out RelationBase>): Relation {
            val rel = Relation(rbClass.java.getAnnotation(Name::class.java)?.value ?: rbClass.java.name)
            AttributeFactory.attributes(rbClass).forEach(rel::attribute)
            return rel
        }

        fun <T : RelationBase>declString(jClass: Class<T>): String {
            return GlobalModule.getRelation(jClass.kotlin)._2s()
        }

        fun <T : RelationBase>instantiateString(obj: T): String {
            return obj.instantiate()._2s()
        }
    }
}
