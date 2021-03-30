package com.penguin.ks7.example;

import com.penguin.ks7.annotation.Order;
import com.penguin.ks7.based.RelationBase;

public class Edge implements RelationBase {
    @Order(0)
    public String n;

    @Order(1)
    public String m;


}
