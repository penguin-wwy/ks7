package com.penguin.ks7.tools

import com.penguin.ks7.element.*
import com.penguin.ks7.platform.SouffleStream

object Example {
    @JvmStatic
    fun main(args: Array<String>) {
        val argList = args.toList()
        var outputFile = "/tmp/example.dl"
        val pos = argList.indexOf("-o")
        if (pos > -1 && argList.size > (pos + 1)) {
            outputFile = argList[pos + 1]
        }
        val ss = SouffleStream(outputFile)
        val edge = Relation("edge").number("x").number("y").input()
        edge.finish(ss)
        val path = Relation("path").number("x").number("y").output()

        val x = Item("x")
        val y = Item("y")
        val z = Item("z")

        path.item(x, y) rule edge.item(x, y)
        path.item(x, y) rule path.item(x, z) and edge.item(z, y)

        path.finish(ss)

        ss.flush()
    }
}