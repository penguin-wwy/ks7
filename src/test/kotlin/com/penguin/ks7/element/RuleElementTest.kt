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

        assertEquals(path._2s(),
        ".decl path(x: number, y: number)\n" +
                "path(x, y) :- edge(x, y).\n" +
                "path(x, y) :- path(x, z), edge(z, y).\n" +
                ".output path\n")

    }

    @Test
    fun testInfix() {
        val edge = DeclElement("edge").number("x").number("y").input()
        val path = DeclElement("path").number("x").number("y").output()

        path.item("x", "y") rule edge.item("x", "y")
        path.item("x", "y") rule path.item("x", "z") and edge.item("z", "y")

        println(path._2s())
        assertEquals(path._2s(),
            ".decl path(x: number, y: number)\n" +
                    "path(x, y) :- edge(x, y).\n" +
                    "path(x, y) :- path(x, z), edge(z, y).\n" +
                    ".output path\n")
    }
}