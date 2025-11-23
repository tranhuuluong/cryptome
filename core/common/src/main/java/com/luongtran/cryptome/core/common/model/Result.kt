package com.luongtran.cryptome.core.common.model

typealias DataState<T> = Result.DataState<T>
typealias DataStateSuccess<T> = Result.DataState.Success<T>
typealias DataStateError = Result.DataState.Error
typealias StateLoading = Result.Loading

sealed interface Result<out T> {
    data object Loading : Result<Nothing>

    sealed interface DataState<out T> : Result<T> {
        data class Error(val exception: Throwable) : DataState<Nothing>
        data class Success<T>(val data: T) : DataState<T>
    }
}

fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is StateLoading -> StateLoading
    is DataStateError -> this
    is DataStateSuccess -> DataStateSuccess(transform(data))
}

fun <T> Result<T>.getOrNull(): T? = when (this) {
    is DataStateSuccess -> data
    else -> null
}

fun <T> Result<List<T>>.getOrEmpty(): List<T> = getOrNull().orEmpty()