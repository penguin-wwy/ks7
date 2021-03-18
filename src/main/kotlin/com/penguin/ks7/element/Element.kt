package com.penguin.ks7.element

import com.penguin.ks7.platform.SouffleStream

interface Element {
    fun _2s(): String

    fun finish(ss: SouffleStream) {
        ss.append(this)
    }
}