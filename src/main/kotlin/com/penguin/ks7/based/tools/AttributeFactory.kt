package com.penguin.ks7.based.tools

import com.penguin.ks7.annotation.Order
import com.penguin.ks7.based.RelationBase
import com.penguin.ks7.element.Attribute
import com.penguin.ks7.element.Item
import com.penguin.ks7.element.NumberTy
import com.penguin.ks7.element.SymbolTy
import java.lang.reflect.Field
import kotlin.reflect.KClass

object AttributeFactory {
    fun <T : RelationBase>attributes(clazz: KClass<T>): Array<Attribute> {
        return clazz.java.fields
            .filter { it.getAnnotation(Order::class.java) != null }
            .map { Pair<Int, Field>(it.getAnnotation(Order::class.java).value, it) }
            .toList()
            .sortedBy { it.first }
            .map { it.second }
            .map(AttributeFactory::fieldToAttribute)
            .toTypedArray()
    }

    fun fieldToAttribute(field: Field): Attribute {
        val name = Item.variable(field.name)
        return when(field.type) {
            String::class.java -> Attribute(name, SymbolTy)
            Int::class.java -> Attribute(name, NumberTy)
            else -> throw AssertionError("TODO")
        }
    }
}