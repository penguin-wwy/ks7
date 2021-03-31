package com.penguin.ks7.based;

import com.penguin.ks7.annotation.Name;
import com.penguin.ks7.annotation.Order;

@Name("Parent")
public class Parent extends RelationBase {
    @Order(0)
    public String n;

    @Order(1)
    public String m;
}