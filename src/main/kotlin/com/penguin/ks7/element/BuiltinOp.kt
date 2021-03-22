package com.penguin.ks7.element

object And : Element {
    override fun _2s() = ","
    override fun toString() = _2s()
}

object Or : Element {
    override fun _2s() = ";"
    override fun toString() = _2s()
}

object Consequence : Element {
    override fun _2s() = ":-"
    override fun toString() = Or._2s()
}