package com.buffup.buffsdk.viewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutinesTestRule : TestWatcher() {
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(dispatcher)

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }

}



fun solution(A: IntArray): Int {
    for( a in 1.. A.max()!!) {
        if (!A.contains(a) ){
                println(a)
                return a
        }
    }
    return A.max()!!+1
}
