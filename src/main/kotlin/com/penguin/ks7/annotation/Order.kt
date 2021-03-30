package com.penguin.ks7.annotation

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Order(val value: Int)