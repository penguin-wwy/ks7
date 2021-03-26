package com.penguin.ks7.element

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ComponentTest {
    @Test
    fun baseTest() {
        val expStr = ".comp MyComponent {\n" +
                "\t.decl TheAnswer(x: number)\n" +
                "}\n"
        val actStr = Component("MyComponent").space {
            this rel Relation("TheAnswer") number "x"
        }._2s()
        assertEquals(expStr, actStr)
    }

    @Test
    fun baseTest2() {
        val expStr = ".comp MyComponent {\n" +
                "\t.decl TheAnswer(x: number)\n" +
                "\tTheAnswer(33).\n" +
                "}\n"
        val actStr = Component("MyComponent").space {
            this rel "TheAnswer" number "x"
            this.instantiate("TheAnswer", Item.integer(33))
        }._2s()
        assertEquals(expStr, actStr)
    }

    @Test
    fun initTest() {
        val comp = Component("MyComponent").space {
            this rel "TheAnswer" number "x"
        }

        val myComp = comp init "myCompInstance1"
        assertEquals(myComp._2s(), ".init myCompInstance1 = MyComponent\n")
        val testRela = Relation("Test") number "x"
        val rule = testRela.item("x") rule myComp.rel("TheAnswer", "x")
        assertEquals("Test(x) :- myCompInstance1.TheAnswer(x).\n", rule._2s())
    }
}