package com.penguin.ks7.element

abstract class Columns(val name: String) : Element

class NumberCol(name: String) : Columns(name) {
    override fun _2s() = "$name: number"
}

class SymbolCol(name: String) : Columns(name) {
    override fun _2s() = "$name: symbol"
}