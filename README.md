## ks7

### English
ks7 (kotlin to [Soufflé](https://github.com/souffle-lang/souffle))

This is a project to generate *Soufflé* code, which is a datalog style programming language, through Kotlin API

The main purpose is to facilitate the easy integration and use of Datalog in JVM-based projects

Provides two different coding-styles:

##### Datalog style
```kotlin
object KtExample {
    @JvmStatic
    fun main(args: Array<String>) {
        println("ks7 example...")

        // use file stream
        val ss = SouffleStream("/tmp/example.dl")
        ss.module.space {
            /*
            * .decl edge(x:number, y:number)
            * .input edge
            * */
            val edge = Relation("edge").input() number "x" number "y" into this
            /*
            * .decl path(x:number, y:number)
            * .output path
            * */
            val path = Relation("path").output() number "x" number "y" into this
            /*
            * path(x, y) :- edge(x, y).
            * */
            path.item("x", "y") rule edge.item("x", "y") into this
            /*
            * path(x, y) :- path(x, z), edge(z, y).
            * */
            path.item("x", "y") rule path.item("x", "z") and edge.item("z", "y") into this
        }

        ss.flush()
    }
}
```

##### Object-oriented style
```java
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

```
Edge.java

```java
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
```
Reachable.java

```java
public class JCExample {

    public static void main(String[] args) {
        try (FileModule fileModule = new FileModule("/tmp/example.dl")) {
            GlobalModule.INSTANCE.setModule(fileModule);
            fileModule.define(Edge.class);
            fileModule.define(Reachable.class);
            fileModule.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```

All sample codes are located in package `com.penguin.ks7.example`

### 中文

ks7 (kotlin to [Soufflé](https://github.com/souffle-lang/souffle)) 是一个通过Kotlin API生成*Datalog*语言`Soufflé`的项目.

主要目的是方便在Jvm系语言的项目中方便地集成、使用Datalog

提供了两种不同风格的编写方法

##### Datalog风格
```kotlin
object KtExample {
    @JvmStatic
    fun main(args: Array<String>) {
        println("ks7 example...")

        // use file stream
        val ss = SouffleStream("/tmp/example.dl")
        ss.module.space {
            /*
            * .decl edge(x:number, y:number)
            * .input edge
            * */
            val edge = Relation("edge").input() number "x" number "y" into this
            /*
            * .decl path(x:number, y:number)
            * .output path
            * */
            val path = Relation("path").output() number "x" number "y" into this
            /*
            * path(x, y) :- edge(x, y).
            * */
            path.item("x", "y") rule edge.item("x", "y") into this
            /*
            * path(x, y) :- path(x, z), edge(z, y).
            * */
            path.item("x", "y") rule path.item("x", "z") and edge.item("z", "y") into this
        }

        ss.flush()
    }
}
```

##### 面向对象风格
```java
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

```
Edge.java

```java
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
```
Reachable.java

```java
public class JCExample {

    public static void main(String[] args) {
        try (FileModule fileModule = new FileModule("/tmp/example.dl")) {
            GlobalModule.INSTANCE.setModule(fileModule);
            fileModule.define(Edge.class);
            fileModule.define(Reachable.class);
            fileModule.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```
调用方式

样例代码位于 `com.penguin.ks7.example`


### Soufflé Features Support
* Relations
    * [ ] B-tree
    * [ ] [Brie](https://souffle-lang.github.io/relations#brie-relations)
    * [ ] [Equivalence](https://souffle-lang.github.io/relations#equivalence-relations)
    * [X] [Nullary](https://souffle-lang.github.io/relations#nullaries)

* Type
    * [x] Primitive Types
    * [x] Equivalence Types
    * [x] Base Types
    * [x] Union Types
    * [ ] [Check Base Type](https://souffle-lang.github.io/types#pitfalls)
    * [x] [Record Types](https://souffle-lang.github.io/types#record-types)
    * [ ] [ADT](https://souffle-lang.github.io/types#algebraic-data-types-adt)

* Strings

* Arithmetic

* Components
    * [x] Base
    * [x] [Inheritance](https://souffle-lang.github.io/components#inheritance)
    * [ ] [Type-Parametrization](https://souffle-lang.github.io/components#type-parametrization)
    * [ ] [Overridable-Relations](https://souffle-lang.github.io/components#overridable-relations)

* Clauses
    * [x] Negation
    * [ ] Disjunction

* Aggregates and Generative Functors
    * [ ] [Aggregates](https://souffle-lang.github.io/aggregates#aggregates)
    * [ ] [Generative-Functors](https://souffle-lang.github.io/aggregates#generative-functors)


### Feature work

* Support all Soufflé features
* More code verification and optimization
* Use Soufflé jni interface to directly generate memory objects instead of generating source code file on disk
