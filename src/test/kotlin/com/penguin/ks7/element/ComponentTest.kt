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
            use("TheAnswer").instantiate(Item.integer(33)) to this
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
        val rule = testRela.item("x") rule myComp.relItem("TheAnswer", "x")
        assertEquals("Test(x) :- myCompInstance1.TheAnswer(x).\n", rule._2s())

        assertEquals("myCompInstance1.TheAnswer(33).\n", myComp.relInt("TheAnswer", Item.integer(33))._2s())
    }

    @Test
    fun ruleTest() {
        val expStr = ".comp Reachability {\n" +
                "\t.decl edge(u: number, v: number)\n" +
                "\t.decl reach(u: number, v: number)\n" +
                "\treach(u, v) :- edge(u, v).\n" +
                "\treach(u, v) :- reach(u, x), edge(x, v).\n" +
                "}\n"
        val actStr = Component("Reachability").space {
            Relation("edge") number "u" number "v" to this
            Relation("reach") number "u" number "v" to this
            use("reach").item("u", "v") rule use("edge").item("u", "v") to this
            use("reach").item("u", "v") rule use("reach").item("u", "x") and use("edge").item("x", "v") to this
        }._2s()
        assertEquals(expStr, actStr)
    }

    @Test
    fun superTest() {
        val baseStr = ".comp Base {\n" +
                "\t.decl TheAnswer(x: number)\n" +
                "\tTheAnswer(x) :- 42.\n" +
                "}\n"
        val baseComp = Component("Base").space {
            Relation("TheAnswer") number "x" to this
            use("TheAnswer").item("x") rule Item.integer(42) to this
        }
        assertEquals(baseStr, baseComp._2s())

        val childStr = ".comp Child : Base {\n" +
                "\t.decl WhatIsTheAnswer(n: number)\n" +
                "\tWhatIsTheAnswer(n) :- TheAnswer(n).\n" +
                "}\n"
        val childComp = Component("Child").superComponent(baseComp).space {
            Relation("WhatIsTheAnswer") number "n" to this
            use("WhatIsTheAnswer").item("n") rule use("TheAnswer").item("n") to this
        }
        assertEquals(childStr, childComp._2s())
    }
}