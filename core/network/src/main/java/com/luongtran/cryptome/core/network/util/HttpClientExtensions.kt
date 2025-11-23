package com.luongtran.cryptome.core.network.util

import com.luongtran.cryptome.core.common.model.DataState
import com.luongtran.cryptome.core.common.model.DataStateError
import com.luongtran.cryptome.core.common.model.DataStateSuccess
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): DataState<T> {
    return try {
        execute().toDataState()
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive() // rethrow CancellationException
        DataStateError(e)
    }
}

suspend inline fun <reified T> HttpResponse.toDataState(): DataState<T> = when (status.value) {
    in 200..299 -> DataStateSuccess(body<T>())
    else -> DataStateError(Throwable("Something went wrong!"))
}