package com.penguin.ks7.element

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ClausesTest {
    @Test
    fun test() {
        val edge = Relation("edge").number("x").number("y").input()
        assertEquals(edge._2s(), ".decl edge(x: number, y: number)\n" +
                ".input edge\n")

        val path = Relation("path").number("x").number("y").output()
        assertEquals(path._2s(), ".decl path(x: number, y: number)\n" +
                ".output path\n")

        val baseRule = Clauses(SymbolInstance(path, "x", "y")).start(SymbolInstance(edge, "x", "y"))
        path.rule(baseRule)
        val driveRule = Clauses(SymbolInstance(path, "x", "y"))
            .start(SymbolInstance(path, "x", "z"))
            .and(SymbolInstance(edge, "z", "y"))
        path.rule(driveRule)

        assertEquals("path(x, y) :- edge(x, y).\n" +
                "path(x, y) :- path(x, z), edge(z, y).\n",
            path.rule2s())

    }

    @Test
    fun testInfix() {
        val edge = Relation("edge").number("x").number("y").input()
        val path = Relation("path").number("x").number("y").output()

        path.item("x", "y") rule edge.item("x", "y")
        path.item("x", "y") rule path.item("x", "z") and edge.item("z", "y")

        assertEquals(path._2s(),
            ".decl path(x: number, y: number)\n" +
                    ".output path\n")
        assertEquals(path.rule2s(),
                    "path(x, y) :- edge(x, y).\n" +
                    "path(x, y) :- path(x, z), edge(z, y).\n")
    }

    @Test
    fun testNegation() {
        val canRenovate = Relation("CanRenovate").symbol("person").symbol("building")
        val owner = Relation("Owner").symbol("person").symbol("building")
        val heritage = Relation("Heritage").symbol("building")

        val clauses = canRenovate.item("person", "building") rule
                owner.item("person", "building") and
                heritage.item("building").negation()
        assertEquals(clauses._2s(),
            "CanRenovate(person, building) :- Owner(person, building), !Heritage(building).\n")
    }
}