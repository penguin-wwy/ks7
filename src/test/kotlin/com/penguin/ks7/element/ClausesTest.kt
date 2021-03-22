package com.penguin.ks7.element

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ClausesTest {
    @Test
    fun test() {
        val edge = Relation("edge").number("x").number("y").input()
        val path = Relation("path").number("x").number("y").output()

        val baseRule = Clauses(ClausesItem(path, "x", "y")).start(ClausesItem(edge, "x", "y"))
        path.rule(baseRule)
        val driveRule = Clauses(ClausesItem(path, "x", "y"))
            .start(ClausesItem(path, "x", "z"))
            .and(ClausesItem(edge, "z", "y"))
        path.rule(driveRule)

        assertEquals(path._2s(),
        ".decl path(x: number, y: number)\n" +
                "path(x, y) :- edge(x, y).\n" +
                "path(x, y) :- path(x, z) , edge(z, y).\n" +
                ".output path\n")

    }

    @Test
    fun testInfix() {
        val edge = Relation("edge").number("x").number("y").input()
        val path = Relation("path").number("x").number("y").output()

        path.item("x", "y") rule edge.item("x", "y")
        path.item("x", "y") rule path.item("x", "z") and edge.item("z", "y")

        assertEquals(path._2s(),
            ".decl path(x: number, y: number)\n" +
                    "path(x, y) :- edge(x, y).\n" +
                    "path(x, y) :- path(x, z) , edge(z, y).\n" +
                    ".output path\n")
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
            "CanRenovate(person, building) :- Owner(person, building) , !Heritage(building).")
    }
}