package com.penguin.ks7.based.tools

import com.penguin.ks7.based.RelationBase
import com.penguin.ks7.element.VariableItem

object RelationFactory {
    fun <T: RelationBase>create(clazz: Class<T>, vararg variable: VariableItem): T {
        val result = clazz.getConstructor().newInstance() as T
        result.addItems(variable)
        return result
    }
}