package com.penguin.ks7.example;

import com.penguin.ks7.annotation.Name;
import com.penguin.ks7.annotation.Order;
import com.penguin.ks7.annotation.Rule;
import com.penguin.ks7.based.RelationBase;
import com.penguin.ks7.based.tools.RelationFactory;
import com.penguin.ks7.element.Item;
import com.penguin.ks7.element.VariableItem;

/*
* .decl Reachable(n: symbol, m: symbol)
* */
@Name("Reachable")
public class Reachable extends RelationBase {
    @Order(0)
    public String n;

    @Order(1)
    public String m;


    /*
    * base rule
    * Reachable(x, y):- Edge(x, y).
    * */
    @Rule(base = true)
    public void baseRule() {
        VariableItem x = Item.Companion.variable("x");
        VariableItem y = Item.Companion.variable("y");
        Edge edge = RelationFactory.INSTANCE.create(Edge.class, x, y);
        assertTrue(x, y, edge);
    }

    /*
    * inductive rule
    * Reachable(x, z):- Reachable(x, y), Edge(y, z).
    * */
    @Rule
    public void inductiveRule() {
        VariableItem x = Item.Companion.variable("x");
        VariableItem y = Item.Companion.variable("y");
        VariableItem z = Item.Companion.variable("z");
        Reachable reachable = RelationFactory.INSTANCE.create(Reachable.class, x, y);
        Edge edge = RelationFactory.INSTANCE.create(Edge.class, y, z);
        assertTrue(x, z, reachable.and(edge));
    }
}
