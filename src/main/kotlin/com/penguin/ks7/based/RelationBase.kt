package com.penguin.ks7.based

import com.penguin.ks7.based.tools.AttributeFactory
import com.penguin.ks7.element.Item
import com.penguin.ks7.element.Relation
import com.penguin.ks7.element.VariableItem
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
        // TODO
    }

    internal fun getRelation(): Relation {
        return GlobalModule.getRelation(this::class)
    }

    companion object {
        internal fun toRelation(rbClass: KClass<out RelationBase>): Relation {
            val rel = Relation(rbClass.java.name)
            AttributeFactory.attributes(rbClass).forEach(rel::attribute)
            return rel
        }
    }
}
