package com.penguin.ks7.element

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RelationTest {
    @Test
    fun test() {
        val resultDecl = Relation("result") number "a" number "b" number "c"
        resultDecl.output()
            .fileName("result.csv")
            .delimiter(",")
            .compress()
        assertEquals(
            resultDecl._2s(),
            ".decl result(a: number, b: number, c: number)\n" +
                    ".output result(filename=\"result.csv\", delimiter=\",\", compress=true)\n"
        )
    }

    @Test
    fun testNullaries() {
        val nullAries = Relation("A")
        assertEquals(nullAries._2s(), ".decl A()\n")
    }
}