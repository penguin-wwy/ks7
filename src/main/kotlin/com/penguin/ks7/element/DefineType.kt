package com.penguin.ks7.element

abstract class DefineType(val name: String) : Type

class BaseTypes(name: String, val primitiveTy: PrimitiveTy) : DefineType(name) {
    override fun desc(): String {
        return ".type $name <: ${primitiveTy.desc()}"
    }
}