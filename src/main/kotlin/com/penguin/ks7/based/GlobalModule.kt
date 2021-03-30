package com.penguin.ks7.based

import com.penguin.ks7.element.Element
import com.penguin.ks7.element.Relation
import kotlin.reflect.KClass

object GlobalModule {
    var module: ModuleBase = DefaultModule()

    private val kStore = mutableMapOf<KClass<out Any>, Element>()

    fun getRelation(kClass: KClass<out RelationBase>): Relation {
        return kStore.getOrPut(kClass) { RelationBase.toRelation(kClass) } as Relation
    }
}