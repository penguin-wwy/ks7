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