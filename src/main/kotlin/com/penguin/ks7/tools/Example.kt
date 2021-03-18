package com.penguin.ks7.tools

import com.penguin.ks7.element.DeclElement
import com.penguin.ks7.element.RuleElement
import com.penguin.ks7.element.RuleItem
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
        val edge = DeclElement("edge").number("x").number("y").input()
        edge.finish(ss)
        val path = DeclElement("path").number("x").number("y").output()

        val baseRule = RuleElement(RuleItem(path, "x", "y"), RuleItem(edge, "x", "y"))
        path.rule(baseRule)
        val driveRule = RuleElement(RuleItem(path, "x", "y"), RuleItem(path, "x", "z"), RuleItem(edge, "z", "y"))
        path.rule(driveRule)
        path.finish(ss)

        ss.flush()
    }
}