package com.buffup.buffsdk.utils

import android.content.Context
import com.buffup.sdk.R

object SharedTexts {
    lateinit var context: Context
    fun netWorkError() = context.getString(R.string.networkError)
}
