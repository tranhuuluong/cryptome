package com.luongtran.cryptome.core.domain.mapper

fun String?.toDoubleOrNaN(): Double = this?.toDoubleOrNull() ?: Double.NaN