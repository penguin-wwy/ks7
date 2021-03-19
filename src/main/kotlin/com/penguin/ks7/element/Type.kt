package com.penguin.ks7.element

interface Type : Element {
    fun desc(): String
    override fun _2s() = desc()
}

interface PrimitiveTy : Type

object SymbolTy : PrimitiveTy {
    override fun desc() = "symbol"
}

object NumberTy : PrimitiveTy {
    override fun desc() = "number"
}

object UnsignedTy : PrimitiveTy {
    override fun desc() = "usigned"
}

object FloatTy : PrimitiveTy {
    override fun desc() = "float"
}