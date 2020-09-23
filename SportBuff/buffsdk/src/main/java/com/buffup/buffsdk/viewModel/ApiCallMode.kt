package com.buffup.buffsdk.viewModel

sealed class ApiCallMode {
    open class Force : ApiCallMode()
    class ForceWithRetry : Force()
    class ForceWithoutRetry() : Force()
    class OptionalWithRetry() : ApiCallMode()
    class OptionalWithoutRetry() : ApiCallMode()
    class NoDialog() : ApiCallMode()
}