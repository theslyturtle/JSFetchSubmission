package com.jonathan.slighfetchtakehome.data.util

import retrofit2.HttpException
import java.util.concurrent.CancellationException

suspend fun <RESPONSE> apiCall(function: suspend () -> RESPONSE):
        ApiResult<RESPONSE> {
    return try {
        ApiResult.Success(function.invoke())
    } catch (e: Throwable) {
        when (e) {
            is CancellationException -> ApiResult.Failure(e, null, true)
            is HttpException -> ApiResult.Failure(e, e.code(), false)
            else -> ApiResult.Failure(e, null, false)
        }
    }
}

sealed class ApiResult<out T> constructor(protected val _value: T?) {
    class Success<out T>(value: T) : ApiResult<T>(value) {
        val value = _value!!
    }

    class Failure<out T>(val exception: Throwable, val responseCode: Int?, val cancelled: Boolean) :
        ApiResult<T>(null)
}
