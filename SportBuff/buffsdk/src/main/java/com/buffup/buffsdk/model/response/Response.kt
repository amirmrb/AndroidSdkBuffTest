package com.buffup.buffsdk.model.response

sealed class Response {
    data class Success<T>(val t:T)
    data class Failure(val error: Throwable)
}