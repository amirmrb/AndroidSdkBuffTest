package com.buffup.buffsdk.view.view.custom

import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.buffup.buffsdk.view.MockActivity
import com.buffup.buffsdk.view.custom.SecondCountDownTimer
import com.buffup.buffsdk.view.utils.lazyActivityScenarioRule
import com.buffup.sdk.R
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SecondCountDownTimerTest {
    @get:Rule
    val rule =
        lazyActivityScenarioRule<MockActivity>(
            launchActivity = false
        )

    private lateinit var secondCountDownTimer: SecondCountDownTimer
    private var isFinished = false
    private var tickCount = 0

    private val onFinishedAction: () -> Unit = { isFinished = true }
    private val onTickAction: (Int) -> Unit = { _ -> tickCount++ }

    @Before
    fun setup() {
        MockActivity.layout = R.layout.activity_mock
        rule.launch(null)
    }

    @Test
    fun onFinish() {
        rule.getScenario().moveToState(Lifecycle.State.RESUMED).onActivity {
            secondCountDownTimer = SecondCountDownTimer(2, onFinishedAction, onTickAction)
            secondCountDownTimer.start()
            Handler().postDelayed({
                assertEquals(true, isFinished)
            }, 3000)
        }
        Thread.sleep(4000)
    }

    @Test
    fun onTick() {
        rule.getScenario().moveToState(Lifecycle.State.RESUMED).onActivity {
            secondCountDownTimer = SecondCountDownTimer(2, onFinishedAction, onTickAction)
            secondCountDownTimer.start()
            Handler().postDelayed({
                org.junit.Assert.assertEquals(2, tickCount)
            }, 3000)
        }
        Thread.sleep(4000)
    }
}