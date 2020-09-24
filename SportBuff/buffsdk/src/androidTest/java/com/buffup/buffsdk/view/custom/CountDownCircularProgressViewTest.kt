package com.buffup.buffupSdk.view.custom

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.platform.app.InstrumentationRegistry
import com.buffup.buffsdk.view.MockActivity
import com.buffup.buffsdk.view.custom.CountDownCircularProgressView
import com.buffup.buffsdk.view.view.custom.waitFor
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner


@RunWith(BlockJUnit4ClassRunner::class)
class CountDownCircularProgressViewTest {

    lateinit var countDownCircularProgressView: CountDownCircularProgressView
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    val activityScenario: ActivityScenario<MockActivity> =
        ActivityScenario.launch(MockActivity::class.java)

    @Before
    fun setup() {
        countDownCircularProgressView = CountDownCircularProgressView(context)
    }


    @Test(expected = IllegalArgumentException::class)
    fun startWithoutSettingSeconds() {
        countDownCircularProgressView.start()
    }

    @Test
    fun start() {
            countDownCircularProgressView.seconds = 2
            countDownCircularProgressView.start()
            onView(isRoot()).perform(waitFor(1000))
            assertTrue(countDownCircularProgressView.isCounting)
            onView(isRoot()).perform(waitFor(1000))
            assertFalse(countDownCircularProgressView.isCounting)
    }

    @Test
    fun stop() {
        ActivityScenario.ActivityAction<MockActivity> {
            countDownCircularProgressView = CountDownCircularProgressView(context)
            var isFinishedActionCalled = false
            countDownCircularProgressView.seconds = 5
            countDownCircularProgressView.onFinishedAction = { isFinishedActionCalled = true }

            countDownCircularProgressView.start()
            countDownCircularProgressView.stop()

            assertFalse(countDownCircularProgressView.isCounting)
            assertTrue(isFinishedActionCalled)
        }
    }
}