package com.penguin.ks7.element

interface Item : Element {
    companion object {
        fun variable(name: String): VariableItem {
            return VariableItem(name)
        }

        fun symbol(data: String): SymbolItem {
            return SymbolItem(data)
        }

        fun integer(data: Int): IntItem {
            return IntItem(data)
        }
    }
}

class VariableItem(val name: String) : Item {

    override fun _2s(): String {
        return name
    }

    override fun toString() = _2s()
}

class SymbolItem(val data: String) : Item {
    override fun _2s(): String {
        return "\"$data\""
    }

    override fun toString() = _2s()
}

class IntItem(val data: Int) : Item {
    override fun _2s(): String {
        return data.toString()
    }

    override fun toString() = _2s()
}

class ComplexItem private constructor(val items: Array<out Item>) : Item {
    override fun _2s(): String {
        return "[" + items.joinToString { it._2s() } + "]"
    }

    override fun toString() = _2s()

    companion object {
        fun create(vararg inputs: Item) = ComplexItem(inputs)
        fun endNil(vararg inputs: Int) = ComplexItem(arrayOf(*inputs.map { IntItem(it) }.toTypedArray(), NilItem))
    }
}

object NilItem : Item {
    override fun _2s(): String {
        return "nil"
    }

    override fun toString() = _2s()
}