package com.penguin.ks7.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Rule(val base: Boolean = false)