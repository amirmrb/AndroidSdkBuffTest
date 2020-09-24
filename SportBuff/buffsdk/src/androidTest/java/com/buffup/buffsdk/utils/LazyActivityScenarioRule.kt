package com.buffup.buffsdk.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.junit.rules.ExternalResource

class LazyActivityScenarioRule<A : Activity> : ExternalResource {

    constructor(launchActivity: Boolean, startActivityIntentSupplier: () -> Intent) {
        this.launchActivity = launchActivity
        scenarioSupplier = { ActivityScenario.launch<A>(startActivityIntentSupplier()) }
    }

    constructor(launchActivity: Boolean, startActivityIntent: Intent) {
        this.launchActivity = launchActivity
        scenarioSupplier = { ActivityScenario.launch<A>(startActivityIntent) }
    }

    constructor(launchActivity: Boolean, startActivityClass: Class<A>) {
        this.launchActivity = launchActivity
        scenarioSupplier = { ActivityScenario.launch<A>(startActivityClass) }
    }

    private var launchActivity: Boolean

    private var scenarioSupplier: () -> ActivityScenario<A>

    private var scenario: ActivityScenario<A>? = null

    private var scenarioLaunched: Boolean = false

    override fun before() {
        if (launchActivity) {
            launch()
        }
    }

    override fun after() {
        scenario?.close()
    }

    fun launch(newIntent: Intent? = null) {
        if (scenarioLaunched) throw IllegalStateException("Scenario has already been launched!")

        newIntent?.let { scenarioSupplier = { ActivityScenario.launch<A>(it) } }

        scenario = scenarioSupplier()
        scenarioLaunched = true
    }

    fun getScenario(): ActivityScenario<A> = checkNotNull(scenario)
}

inline fun <reified A : Activity> lazyActivityScenarioRule(launchActivity: Boolean = true, noinline intentSupplier: () -> Intent): LazyActivityScenarioRule<A> =
    LazyActivityScenarioRule(
        launchActivity,
        intentSupplier
    )

inline fun <reified A : Activity> lazyActivityScenarioRule(launchActivity: Boolean = true, intent: Intent? = null): LazyActivityScenarioRule<A> = if (intent == null) {
    LazyActivityScenarioRule(
        launchActivity,
        A::class.java
    )
} else {
    LazyActivityScenarioRule(
        launchActivity,
        intent
    )
}



fun waitFor(delay: Long): ViewAction {
    return object : ViewAction {
        override fun perform(uiController: UiController?, view: View?) {
            uiController?.loopMainThreadForAtLeast(delay)
        }

        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isRoot()
        }

        override fun getDescription(): String {
            return "wait for " + delay + "milliseconds"
        }
    }
}