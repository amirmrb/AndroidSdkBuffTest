package com.buffup.buffsdk.view.view.custom

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.buffup.buffsdk.view.MockActivity
import com.buffup.buffupSdk.view.custom.CircleProgressBar
import com.buffup.buffupSdk.view.custom.ProgressBarAnimation
import org.hamcrest.Matcher
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ProgressBarAnimationTest {
    private lateinit var circularProgressView: CircleProgressBar
    private lateinit var view: View

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        view = View(context)
        circularProgressView = CircleProgressBar(context)
    }

    @Test
    fun afterAnimation_ProgressShouldBeMax() {
        val activityScenario: ActivityScenario<MockActivity> =
            ActivityScenario.launch(MockActivity::class.java)
        activityScenario.onActivity {
            val progressBarAnimation = ProgressBarAnimation(circularProgressView, 0f, 1f)
            progressBarAnimation.duration = 1000
            view.startAnimation(progressBarAnimation)
            onView(isRoot())
            Assert.assertEquals(circularProgressView.max, circularProgressView.progress)
        }
    }
}

fun waitFor(delay: Long): ViewAction {
    return object : ViewAction {
        override fun perform(uiController: UiController?, view: View?) {
            uiController?.loopMainThreadForAtLeast(delay)
        }

        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun getDescription(): String {
            return "wait for " + delay + "milliseconds"
        }
    }
}