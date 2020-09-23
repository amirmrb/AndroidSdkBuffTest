package com.buffup.buffsdk.viewModel

data class RetryExceptionModel(
    val code: Int,
    val message: String,
    val block: () -> Unit,
    val retryMode: ApiCallMode = ApiCallMode.OptionalWithoutRetry(),
    val exit: () -> Unit
)