package com.penguin.ks7.element

class Attribute(val name: Item, val type: Type) : Element {
    override fun _2s(): String {
        return "$name: ${type.desc()}"
    }

    companion object {
        fun create(name: String, type: Type)  = Attribute(Item.variable(name), type)
    }
}

infix fun Relation.number(item: Item): Relation {
    return this.attribute(Attribute(item, NumberTy))
}

infix fun Relation.number(name: String) = number(Item.variable(name))

infix fun Relation.symbol(item: Item): Relation {
    return this.attribute(Attribute(item, SymbolTy))
}

infix fun Relation.symbol(name: String) = symbol(Item.variable(name))