package com.penguin.ks7.element

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TypeTest {
    @Test
    fun equivalenceTest() {
        val myNumber = EquivalenceTy("myNumber") assign NumberTy
        assertEquals(".type myNumber = number\n", myNumber._2s())
    }

    @Test
    fun baseTypeTest() {
        val city = BaseTy("City") defined SymbolTy
        assertEquals(".type City <: symbol\n", city._2s())

        val town = BaseTy("Town", SymbolTy)
        assertEquals(".type Town <: symbol\n", town._2s())
    }

    @Test
    fun unionTypeTest() {
        val postCode = BaseTy("PostCodes") defined NumberTy
        val zipCode = BaseTy("ZipCodes") defined NumberTy
        val postalCode = UnionType("PostalCode") union postCode union zipCode
        assertEquals(".type PostalCode = PostCodes | ZipCodes\n", postalCode._2s())
    }

    @Test
    fun unionTypeExcept() {
        val weekDays = BaseTy("Weekdays") defined SymbolTy
        val dates = BaseTy("Dates") defined NumberTy
        assertThrows(TypeConflict::class.java) {
            UnionType("Days") union weekDays union dates
        }
    }

    @Test
    fun useUnionType() {
        val city = BaseTy("City") defined SymbolTy
        assertEquals(".type City <: symbol\n", city._2s())

        val town = BaseTy("Town", SymbolTy)
        assertEquals(".type Town <: symbol\n", town._2s())

        val village = BaseTy("Village", SymbolTy)
        assertEquals(".type Village <: symbol\n", village._2s())

        val place = UnionType("Place") union city union town union village
        assertEquals(".type Place = City | Town | Village\n", place._2s())

        val c = Attribute.create("c", city)
        val t = Attribute.create("t", town)
        val v = Attribute.create("v", village)
        val data = Relation("Data") attribute c attribute t attribute v
        assertEquals(".decl Data(c: City, t: Town, v: Village)\n", data._2s())
    }

    @Test
    fun recordTypeTest() {
        val city = BaseTy("City") defined SymbolTy
        val town = BaseTy("Town", SymbolTy)
        val village = BaseTy("Village", SymbolTy)
        val place = UnionType("Place") union city union town union village

        val connection = RecordType("Connection").value("from", place).value("to", place)
        assertEquals(".type Connection = [\n" +
                "\tfrom : Place,\n" +
                "\tto : Place\n" +
                "]\n", connection._2s())

        val list = RecordType("List").value("head", NumberTy).nested("tail")
        assertEquals(".type List = [\n" +
                "\thead : number,\n" +
                "\ttail : List\n" +
                "]\n", list._2s())

        val a = Relation("A") attribute Attribute.create("x", list)
        assertEquals(".decl A(x: List)\n", a._2s())

        assertEquals("A(nil).\n", a.instantiate(NilItem)._2s())
        assertEquals("A([1, nil]).\n", a.instantiate(ComplexItem.endNil(1))._2s())
        assertEquals("A([2, [3, nil]]).\n", a.instantiate(ComplexItem.create(IntItem(2), ComplexItem.endNil(3)))._2s())
    }
}