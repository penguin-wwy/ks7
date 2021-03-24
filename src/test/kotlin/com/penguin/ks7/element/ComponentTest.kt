package com.penguin.ks7.element

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ComponentTest {
    @Test
    fun baseTes() {
        val expStr = ".comp MyComponent {\n" +
                "\t.decl TheAnswer(x: number)\n" +
                "}\n"
        val actStr = Component("MyComponent").space {
            it rel Relation("TheAnswer") number "x"
        }._2s()
        assertEquals(actStr, expStr)
    }

    @Test
    fun initTest() {
        val comp = Component("MyComponent").space {
            it rel "TheAnswer" number "x"
        }

        val myComp = comp init "myCompInstance1"
        assertEquals(myComp._2s(), ".init myCompInstance1 = MyComponent\n")
        val testRela = Relation("Test") number "x"
        testRela.item("x") rule myComp.rel("TheAnswer", "x")
        assertEquals(testRela._2s(), ".decl Test(x: number)\n" +
                "Test(x) :- myCompInstance1.TheAnswer(x).\n")
    }
}