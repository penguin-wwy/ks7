package com.penguin.ks7.element

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RuleElementTest {
    @Test
    fun test() {
        val edge = DeclElement("edge").number("x").number("y").input()
        val path = DeclElement("path").number("x").number("y").output()

        val baseRule = RuleElement(RuleItem(path, "x", "y"), RuleItem(edge, "x", "y"))
        path.rule(baseRule)
        val driveRule = RuleElement(RuleItem(path, "x", "y"), RuleItem(path, "x", "z"), RuleItem(edge, "z", "y"))
        path.rule(driveRule)

        println(path._2s())

    }
}