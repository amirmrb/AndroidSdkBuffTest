package com.buffup.buffsdk.utils

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObservers() {
    value = value
}



