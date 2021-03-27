package com.penguin.ks7.element

interface Element {
    fun _2s(): String
}

infix fun <ME : Element>ME.into(eg: ElementGraph): ME {
    eg.register(this)
    return this
}