package com.buffup.buffsdk.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffup.buffsdk.utils.exceptionHandler
import com.buffup.buffsdk.utils.getErrorCode
import com.buffup.buffsdk.utils.getErrorMessage
import com.buffup.buffsdk.utils.notifyObservers
import com.buffup.sdk.BuildConfig
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    val defaultExitAction = {}
    val defaultApiMode = ApiCallMode.ForceWithRetry()
    val showErrorMutableLiveData = MutableLiveData<ErrorModelWithRetryAction>()

    fun <T : Any?> apiCall(
        block: suspend () -> T,
        result: (T) -> Unit,
        mode: ApiCallMode = defaultApiMode
    ) {
        viewModelScope.launch(viewModelScope.coroutineContext +
                exceptionHandler(block) { _, throwable, retryBlock ->
                    if (BuildConfig.DEBUG)
                        throwable.printStackTrace() // and anything you want except ui ...
                    val code = getErrorCode(throwable)
                    val message = getErrorMessage(code, throwable)
                    showErrorMutableLiveData.value =
                        ErrorModelWithRetryAction(code = code,
                            message = message,
                            block = { apiCall(block, result, mode) },
                            retryMode = mode,
                            exit = defaultExitAction)
                    showErrorMutableLiveData.notifyObservers()
                }) {
            result.invoke(block.invoke())
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

