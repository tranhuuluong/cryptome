package com.luongtran.cryptome.core.common.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <E, D> Flow<List<E>>.mapItems(
    crossinline transform: (E) -> D
): Flow<List<D>> = map { list -> list.map(transform) }