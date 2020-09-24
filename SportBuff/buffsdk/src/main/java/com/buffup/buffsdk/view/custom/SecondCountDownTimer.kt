package com.buffup.buffsdk.view.custom

import android.os.CountDownTimer

class SecondCountDownTimer(
    seconds: Int,
    val onFinishedAction: () -> Unit,
    val onTickAction: (Int) -> Unit
) : CountDownTimer(seconds * 1000L, 1000) {
    override fun onFinish() {
        onFinishedAction()
    }

    override fun onTick(millisUntilFinished: Long) {
        onTickAction(millisUntilFinished.toInt() / 1000)
    }
}