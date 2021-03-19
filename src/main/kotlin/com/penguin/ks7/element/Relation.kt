package com.penguin.ks7.element

enum class IOType {
    INPUT,
    OUTPUT,
    NONE
}

class Relation(name: String) : StmtElement(name) {

    private var ioType: IOType =
        IOType.NONE
    private val elems = mutableListOf<Element>()
    private lateinit var filename: String
    private lateinit var delimiterOp: String
    private var compress: Boolean = false

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

    fun addCol(e: Columns): Relation {
        elems.add(e)
        return this
    }

    fun addCol(vararg e: Columns): Relation {
        elems.addAll(e)
        return this
    }

    fun addCol(es: Collection<Columns>): Relation {
        elems.addAll(es)
        return this
    }

    fun number(s: String) = addCol(NumberCol(s))

    fun number(vararg s: String) = addCol(s.toList().map { NumberCol(it) }.toList())

    fun symbol(s: String) = addCol(SymbolCol(s))

    fun symbol(vararg s: String) = addCol(s.toList().map { SymbolCol(it) }.toList())

    private val rules = mutableListOf<Clauses>()

    fun rule(rule: Clauses): Relation {
        rules.add(rule)
        return this
    }

    fun rules(): String {
        return if (rules.isEmpty()) "" else rules.joinToString("\n") + "\n"
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

    private fun expect() = when (ioType) {
        IOType.INPUT -> ".input $name${options()}"
        IOType.OUTPUT -> ".output $name${options()}"
        else -> ""
    }

    override fun _2s(): String {
        return ".decl $name(${elems.map { it._2s() }.joinToString()})\n" +
                rules() +
                expect() + "\n"
    }
}