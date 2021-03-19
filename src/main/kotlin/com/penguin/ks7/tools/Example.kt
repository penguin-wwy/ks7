package com.penguin.ks7.tools

import com.penguin.ks7.element.Relation
import com.penguin.ks7.element.Clauses
import com.penguin.ks7.element.ClausesItem
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

        val baseRule = Clauses(ClausesItem(path, "x", "y"), ClausesItem(edge, "x", "y"))
        path.rule(baseRule)
        val driveRule = Clauses(ClausesItem(path, "x", "y"), ClausesItem(path, "x", "z"), ClausesItem(edge, "z", "y"))
        path.rule(driveRule)
        path.finish(ss)

        ss.flush()
    }
}