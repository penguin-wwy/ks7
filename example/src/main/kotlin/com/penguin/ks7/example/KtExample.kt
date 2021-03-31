package com.penguin.ks7.example

import com.penguin.ks7.element.*
import com.penguin.ks7.platform.SouffleStream

object KtExample {
    @JvmStatic
    fun main(args: Array<String>) {
        println("ks7 example...")

        // use file stream
        val ss = SouffleStream("/tmp/example.dl")
        ss.module.space {
            /*
            * .decl edge(x:number, y:number)
            * .input edge
            * */
            val edge = Relation("edge").input() number "x" number "y" into this
            /*
            * .decl path(x:number, y:number)
            * .output path
            * */
            val path = Relation("path").output() number "x" number "y" into this
            /*
            * path(x, y) :- edge(x, y).
            * */
            path.item("x", "y") rule edge.item("x", "y") into this
            /*
            * path(x, y) :- path(x, z), edge(z, y).
            * */
            path.item("x", "y") rule path.item("x", "z") and edge.item("z", "y") into this
        }

        ss.flush()
    }
}