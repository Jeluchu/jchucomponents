package com.jeluchu.jchucomponentscompose.core.extensions.numbers

import java.math.RoundingMode

fun Float.roundTo(n: Int): Float = toBigDecimal().setScale(n, RoundingMode.UP).toFloat()

fun Double.roundTo(n: Int): Double = toBigDecimal().setScale(n, RoundingMode.UP).toDouble()