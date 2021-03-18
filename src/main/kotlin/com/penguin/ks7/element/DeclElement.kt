package com.penguin.ks7.element

enum class IOType {
    INPUT,
    OUTPUT,
    NONE
}

class DeclElement(val name: String) : Element {

    private var ioType: IOType =
        IOType.NONE
    private val elems = mutableListOf<Element>()
    private lateinit var filename: String
    private lateinit var delimiterOp: String
    private var compress: Boolean = false

    fun input(): DeclElement {
        ioType = IOType.INPUT
        return this
    }

    fun output(): DeclElement {
        ioType = IOType.OUTPUT
        return this
    }

    fun fileName(s: String): DeclElement {
        filename = s
        return this
    }

    fun delimiter(s: String): DeclElement {
        delimiterOp = s
        return this
    }

    fun compress(): DeclElement {
        compress = true
        return this
    }

    fun addCol(e: Columns): DeclElement {
        elems.add(e)
        return this
    }

    fun addCol(vararg e: Columns): DeclElement {
        elems.addAll(e)
        return this
    }

    fun addCol(es: Collection<Columns>): DeclElement {
        elems.addAll(es)
        return this
    }

    fun number(s: String) = addCol(NumberCol(s))

    fun number(vararg s: String) = addCol(s.toList().map { NumberCol(it) }.toList())

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
                expect()
    }
}