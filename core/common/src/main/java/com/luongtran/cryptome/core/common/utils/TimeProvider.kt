package com.luongtran.cryptome.core.common.utils

import kotlin.time.Clock
import kotlin.time.Instant

interface TimeProvider {
    fun now(): Instant
}

class TimeProviderImpl : TimeProvider {
    override fun now(): Instant = Clock.System.now()
}