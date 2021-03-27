package com.penguin.ks7.element

enum class BuiltinOp {
    CONSEQUENCE,
    AND,
    OR;

    companion object {
        fun toString(op: BuiltinOp) = when (op) {
            CONSEQUENCE -> ":- "
            AND -> ", "
            OR -> "; "
        }
    }
}