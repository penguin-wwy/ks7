package com.penguin.ks7.element

class RuleItem(val owner: DeclElement, vararg s: String) : Element {
    private val items: Array<out String> = s

    override fun _2s() = "${owner.name}(${items.joinToString()})"

    override fun toString(): String {
        return _2s()
    }
}

class RuleElement (private val owner: RuleItem, vararg itemArray: RuleItem) : Element {
    private val items: Array<out RuleItem> = itemArray
    override fun _2s() = "$owner :- ${items.joinToString()}."

    override fun toString(): String {
        return _2s()
    }
}