package com.buffup.buffsdk.repo

import com.buffup.buffsdk.viewModel.CoroutinesTestRule
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {
        @ExperimentalCoroutinesApi
        @get:Rule
        var coroutinesRule = CoroutinesTestRule()
    @Test
    fun `getBuff returns a successfull response`() {
        runBlocking {
            assertNotNull(RetrofitProvider.getRemoteService().getBuff(1))
        }
    }

    @Test(expected = RuntimeException::class)
    fun `getBuff error`() {
            throw RuntimeException("")
    }

}