package com.buffup.buffsdk.view.view

import com.buffup.buffsdk.view.MockActivity
import com.buffup.buffsdk.view.utils.lazyActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Rule

class QuestionOverVideoTest {

    @get:Rule
    val rule =
        lazyActivityScenarioRule<MockActivity>(
            launchActivity = false
        )

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
    fun start() {

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