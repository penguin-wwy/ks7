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

abstract class NamedType(name: String) : Type, NamedElement(name) {
    override fun desc() = name
}

class EquivalenceTy(name: String) : NamedType(name) {
    private lateinit var otherTy: Type

    constructor(name: String, other: Type) : this(name) {
        otherTy = other
    }

    infix fun assign(other: Type): EquivalenceTy {
        otherTy = other
        return this
    }

    override fun desc(): String {
        return ".type $name = ${otherTy.desc()}\n"
    }
}

class BaseTy(name: String) : NamedType(name) {
    internal lateinit var otherTy: Type

    constructor(name: String, other: Type) : this(name) {
        otherTy = other
    }

    infix fun defined(other: Type): BaseTy {
        otherTy = other
        return this
    }

    override fun _2s(): String {
        return ".type $name <: ${otherTy.desc()}\n"
    }
}

class UnionType(name: String) : NamedType(name) {
    private val unifies = mutableListOf<BaseTy>()

    infix fun union(baseTy: BaseTy): UnionType {
        if (unifies.isNotEmpty() && unifies.last().otherTy != baseTy.otherTy) {
            throw TypeConflict("type conflict in union type $name")
        }
        unifies.add(baseTy)
        return this
    }

    override fun _2s(): String {
        return ".type $name = ${unifies.joinToString(" | ") { it.name }}\n"
    }
}

class RecordType(name: String) : NamedType(name) {
    private val values = mutableListOf<Pair<String, Type>>()

    infix fun value(v: Pair<String, Type>): RecordType {
        values.add(v)
        return this
    }

    fun value(n: String, t: Type): RecordType {
        return value(Pair(n, t))
    }

    infix fun nested(n: String): RecordType {
        return value(n, this)
    }

    private fun value2s(): String {
        return values.joinToString(",\n\t", "\n\t", "\n") {
            it.first + " : " + it.second.desc()
        }
    }

    override fun _2s(): String {
        return ".type $name = [${value2s()}]\n"
    }
}