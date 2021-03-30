package com.penguin.ks7.example;

import com.penguin.ks7.based.FileModule;
import com.penguin.ks7.based.GlobalModule;

public class JCExample {

    public static void main(String[] args) {
        try (FileModule fileModule = new FileModule("/tmp/example.gdl")) {
            GlobalModule.INSTANCE.setModule(fileModule);
            fileModule.define(Edge.class);
            fileModule.define(Reachable.class);
        }
    }
}
