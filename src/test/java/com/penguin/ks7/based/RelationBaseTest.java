package com.penguin.ks7.based;

import com.penguin.ks7.based.tools.RelationFactory;
import com.penguin.ks7.element.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RelationBaseTest {

    @Test
    public void testDecl() {
        assertEquals(".decl Parent(n: symbol, m: symbol)\n", Parent.Companion.declString(Parent.class));
        Parent example = RelationFactory.INSTANCE
                .create(Parent.class, Item.Companion.variable("x"), Item.Companion.variable("y"));
        assertEquals("Parent(x, y)", Parent.Companion.instantiateString(example));
    }
}