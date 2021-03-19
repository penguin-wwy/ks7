package com.penguin.ks7.element

class RuleItem(val owner: DeclElement, vararg s: String) : Element {
    private val items: Array<out String> = s

    override fun _2s() = "${owner.name}(${items.joinToString()})"

    override fun toString(): String {
        return _2s()
    }
}

fun DeclElement.item(vararg s: String): RuleItem {
    return RuleItem(this, *s)
}

class RuleElement (private val owner: RuleItem, vararg itemArray: RuleItem) : Element {
    private val items = itemArray.toMutableList()
    override fun _2s() = "$owner :- ${items.joinToString()}."

    override fun toString(): String {
        return _2s()
    }

    infix fun and(item: RuleItem): RuleElement {
        items.add(item)
        return this
    }
}

infix fun RuleItem.rule(item: RuleItem): RuleElement {
    val ruleElement = RuleElement(this, item)
    this.owner.rule(ruleElement)
    return ruleElement
}