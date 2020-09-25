package com.buffup.buffsdk.view

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import com.buffup.buffsdk.view.MockActivity
import com.buffup.buffsdk.view.BuffView
import com.buffup.buffsdk.utils.lazyActivityScenarioRule
import com.buffup.buffsdk.view.view.custom.waitFor
import com.buffup.sdk.R
import org.junit.Before
import org.junit.Test

import org.junit.Rule

class BuffViewTest {

    @get:Rule
    val rule =
        lazyActivityScenarioRule<MockActivity>(
            launchActivity = false
        )

    @Before
    @Throws(Exception::class)
    fun setup() {
        MockActivity.layout = R.layout.activity_mock
    }


    @Test
    fun start() {
        rule.launch()
        rule.getScenario().onActivity { activity ->
            val qov = BuffView(activity)
            qov.show()
            Espresso.onView(ViewMatchers.isRoot()).perform(waitFor(1000))
        }
    }

    @Test
    fun getStatus() {
    }

    @Test
    fun setStatus() {
    }

    @Test
    fun init() {
    }

    @Test(expected = IllegalArgumentException::class)
    fun startWithoutInit() {

    }

    @Test
    fun getRemainingTime() {
    }

    @Test
    fun submitAnswer() {
    }

    @Test
    fun close() {
    }
}