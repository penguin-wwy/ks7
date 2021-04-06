package com.penguin.ks7.element

class Component(name: String) : NamedElement(name), Element, ElementGraph {
    override val elements = mutableListOf<Element>()
    private val namedRelation = mutableMapOf<String, Relation>()
    private val parameterTypes = mutableListOf<ParameterType>()
    private lateinit var superComp: Component

    fun superComponent(component: Component): Component {
        superComp = component
        return this
    }

    infix fun rel(name: String): Relation {
        return rel(Relation(name))
    }

    infix fun rel(rel: Relation): Relation {
        namedRelation[rel.name] = super.register(rel)
        return rel
    }

    fun use(name: String): Relation {
        return namedRelation.getOrElse(name) {
            superComp.namedRelation[name] ?: throw AssertionError("No such relation: $name")
        }
    }

    fun paramTy(pTy: ParameterType): Component {
        parameterTypes.add(pTy)
        return this
    }

    override fun <T : Element> register(element: T): T {
        return when (element) {
            is Relation -> rel(element) as T
            is Instance, is Clauses -> super.register(element)
            else -> element
        }
    }

    private fun paramTy2s(): String {
        return if (parameterTypes.isNotEmpty()) "<${parameterTypes.joinToString { it.desc() }}>" else ""
    }

    private fun super2s(): String {
        return if (this::superComp.isInitialized) ": ${superComp.name} " else ""
    }

    override fun _2s(): String {
        return ".comp $name${paramTy2s()} ${super2s()}{\n${joinToString(prefix = "\t")}}\n"
    }
}