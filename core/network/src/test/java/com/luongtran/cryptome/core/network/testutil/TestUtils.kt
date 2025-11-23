package com.luongtran.cryptome.core.network.testutil

import java.io.InputStream

fun Any.loadResource(name: String): InputStream =
    this::class.java.getResourceAsStream("/$name") ?: error("Resource $name not found")