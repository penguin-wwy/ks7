package com.penguin.ks7.element

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RelationTest {
    @Test
    fun test() {
        val resultDecl = Relation("result")
            .number("a", "b", "c")
            .output()
            .fileName("result.csv")
            .delimiter(",")
            .compress()
        assertEquals(
            resultDecl._2s(),
            ".decl result(a: number, b: number, c: number)\n" +
                    ".output result(filename=\"result.csv\", delimiter=\",\", compress=true)"
        )
    }
}