package com.penguin.ks7.example;

import com.penguin.ks7.annotation.Name;
import com.penguin.ks7.annotation.Order;
import com.penguin.ks7.based.RelationBase;

/*
* .decl Edge(n: number, m: number)
* */
@Name("Edge")
public class Edge extends RelationBase {
    @Order(0)
    public String n;

    @Order(1)
    public String m;
}
