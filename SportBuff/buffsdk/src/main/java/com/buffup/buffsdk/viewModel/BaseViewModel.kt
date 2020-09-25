package com.buffup.buffsdk.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffup.buffsdk.utils.exceptionHandler
import com.buffup.buffsdk.utils.getErrorCode
import com.buffup.buffsdk.utils.getErrorMessage
import com.buffup.buffsdk.utils.notifyObservers
import com.buffup.sdk.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

open class BaseViewModel() :
    ViewModel() {
    val defaultExitAction = {}
    val defaultApiMode = ApiCallMode.ForceWithRetry()
    val showErrorMutableLiveData = MutableLiveData<ErrorModelWithRetryAction>()

    fun <T : Any?> apiCall(
        block: suspend () -> T,
        result: (T) -> Unit,
        mode: ApiCallMode = defaultApiMode
    ) {
        val handler: (CoroutineContext, Throwable, suspend () -> T) -> Unit = { _, throwable, _ ->
            if (BuildConfig.DEBUG)
                throwable.printStackTrace() // and anything you want except ui ...
            val code = getErrorCode(throwable)
            val message = getErrorMessage(code, throwable)
            showErrorMutableLiveData.value =
                ErrorModelWithRetryAction(
                    code = code,
                    message = message,
                    block = { apiCall(block, result, mode) },
                    retryMode = mode,
                    exit = defaultExitAction
                )
            showErrorMutableLiveData.notifyObservers()
        }
        viewModelScope.launch(
            viewModelScope.coroutineContext + exceptionHandler(block, handler)
        ) {
            result.invoke(block.invoke())
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun delayDo(waitTimeInMillis: Long, function: (Unit) -> Unit) {
        apiCall({ delay(waitTimeInMillis) }, function)
    }
}

open class CoroutineContextProvider {
    open val IO: CoroutineContext by lazy { Dispatchers.IO }
}
