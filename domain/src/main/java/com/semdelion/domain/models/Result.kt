package com.semdelion.domain.models

typealias Mapper<Input, Output> =  (Input) -> Output

sealed class Result<T> {

    fun <R> map(mapper: Mapper<T, R>? = null): Result<R> = when(this) {
        is LoadingResult -> LoadingResult()
        is ErrorResult -> ErrorResult(this.exception)
        is SuccessResult -> {
            if (mapper == null)
                throw IllegalAccessException("Mapper should not be NULL for success result")
            SuccessResult(mapper(this.data))
        }
    }
}

sealed class FinalResult<T>: Result<T>()

class LoadingResult<T> : Result<T>()

class SuccessResult<T>(
    val data: T
) : FinalResult<T>()

class ErrorResult<T>(
    val exception: Exception
) : FinalResult<T>()

fun <T> Result<T>?.takeSuccess(): T? {
    return if(this is SuccessResult) this.data else null
}