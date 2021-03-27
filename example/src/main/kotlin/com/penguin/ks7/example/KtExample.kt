package com.penguin.ks7.example

import com.penguin.ks7.element.*
import com.penguin.ks7.platform.SouffleStream

object KtExample {
    @JvmStatic
    fun main(args: Array<String>) {
        println("ks7 example...")

        val ss = SouffleStream("/tmp/example.dl")
        ss.module.space {
            val edge = Relation("edge").input() number "x" number "y" into this
            val path = Relation("path").output() number "x" number "y" into this
            path.item("x", "y") rule edge.item("x", "y") into this
            path.item("x", "y") rule path.item("x", "z") and edge.item("z", "y") into this
        }

        ss.flush()
    }
}