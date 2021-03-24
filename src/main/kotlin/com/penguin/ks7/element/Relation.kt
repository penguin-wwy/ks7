package com.penguin.ks7.element

enum class IOType {
    INPUT,
    OUTPUT,
    NONE
}

class Relation(name: String) : StmtElement(name) {

    private var ioType: IOType =
        IOType.NONE
    private val attributes = mutableListOf<Attribute>()
    private lateinit var filename: String
    private lateinit var delimiterOp: String
    private var compress: Boolean = false

    fun attribute(attribute: Attribute): Relation {
        attributes.add(attribute)
        return this
    }

    fun input(): Relation {
        ioType = IOType.INPUT
        return this
    }

    fun output(): Relation {
        ioType = IOType.OUTPUT
        return this
    }

    fun fileName(s: String): Relation {
        filename = s
        return this
    }

    fun delimiter(s: String): Relation {
        delimiterOp = s
        return this
    }

    fun compress(): Relation {
        compress = true
        return this
    }

    private val rules = mutableListOf<Clauses>()

    fun rule(rule: Clauses): Relation {
        rules.add(rule)
        return this
    }

//    fun instantiate():

    private fun rule2s(prefix: String = ""): String {
        return if (rules.isEmpty()) "" else rules.map { prefix + it._2s() }.joinToString("\n") + "\n"
    }

    private fun options(): String {
        val builder = mutableListOf<String>()
        if (this::filename.isInitialized) {
            builder.add("filename=\"$filename\"")
        }
        if (this::delimiterOp.isInitialized) {
            builder.add("delimiter=\"$delimiterOp\"")
        }
        if (compress) {
            builder.add("compress=true")
        }

        return if (builder.isEmpty()) "" else "(${builder.joinToString()})"
    }

    private fun expect(prefix: String = "") = when (ioType) {
        IOType.INPUT -> "$prefix.input $name${options()}\n"
        IOType.OUTPUT -> "$prefix.output $name${options()}\n"
        else -> ""
    }

    override fun _2s(): String {
        return _2s("")
    }

    fun _2s(prefix: String): String {
        return "$prefix.decl $name(${attributes.map { it._2s() }.joinToString()})\n" +
                rule2s(prefix) +
                expect(prefix)
    }
}