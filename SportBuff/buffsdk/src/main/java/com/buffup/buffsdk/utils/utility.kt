package com.buffup.buffsdk.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.buffup.buffsdk.utils.ConnectivityChecker.isOnline
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

inline fun <T> exceptionHandler(
    noinline block: suspend () -> T,
    crossinline handler: (CoroutineContext, Throwable, suspend () -> T) -> Unit
): CoroutineExceptionHandler =
    object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
        override fun handleException(context: CoroutineContext, exception: Throwable) =
            handler.invoke(context, exception, block)
    }

fun getErrorCode(throwable: Throwable): Int {
    var code = UnknownErrorCode
    if (throwable is HttpException)
        code = throwable.code()
    else if (throwable is IOException && !isOnline()) {
        code = ConnectionErrorCode
    }
    return code
}

//TODO define a general error type to cast error body to it and use in everywhere in project
fun getErrorMessage(code: Int, throwable: Throwable): String =
    if (code == ConnectionErrorCode) SharedTexts.netWorkError() else throwable.message.toString()


object ConnectivityChecker {
    lateinit var appContext: Context
    fun isOnline(): Boolean {
        if (!::appContext.isInitialized) throw IllegalStateException("app context must be set.")
        val connMgr =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo //FIXME: fix deprecated
        return networkInfo?.isConnected == true //FIXME: fix deprecated
    }
}