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
            it rel (Relation("TheAnswer") number "x")
        }._2s()
        assertEquals(actStr, expStr)
    }
}