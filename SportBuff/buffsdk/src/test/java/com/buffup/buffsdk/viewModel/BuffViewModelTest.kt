package com.buffup.buffsdk.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.buffup.buffsdk.model.view.BuffViewData
import com.buffup.buffsdk.repo.BuffRepository
import com.buffup.buffsdk.repo.FakeBuffRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BuffViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = CoroutinesTestRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel: BuffViewModel

    @Mock
    lateinit var buffDataObserver: Observer<BuffViewData>

    @Mock
    lateinit var hideBuffDataObserver: Observer<Unit?>

    @Mock
    lateinit var errorObserver: Observer<ErrorModelWithRetryAction>

    @Mock
    lateinit var realRepository: BuffRepository
    val fakeRepository = FakeBuffRepository()

    @ExperimentalCoroutinesApi
    @Test
    fun `viewModel successfully load first question`() {
        coroutinesRule.runBlockingTest {
            `when`(realRepository.getBuff(1)).thenReturn(fakeRepository.simpleFakeBuff())
            viewModel = BuffViewModel(realRepository)
            viewModel.buffViewData.observeForever(buffDataObserver)
            viewModel.initialize()
            verify(buffDataObserver).onChanged(fakeRepository.simpleFakeBuff())
            viewModel.buffViewData.removeObserver(buffDataObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws
    fun `viewModel fails to load data`() {
        coroutinesRule.runBlockingTest {
            `when`(realRepository.getBuff(1)).then { fakeRepository.errorData() }
            viewModel = BuffViewModel(realRepository)
            viewModel.buffViewData.observeForever(buffDataObserver)
            viewModel.showErrorMutableLiveData.observeForever(errorObserver)
            viewModel.initialize()
            verify(errorObserver, atLeastOnce()).onChanged(
                ArgumentMatchers.isA(ErrorModelWithRetryAction::class.java)
            )
            viewModel.buffViewData.removeObserver(buffDataObserver)
            viewModel.showErrorMutableLiveData.removeObserver(errorObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `viewModel loads other questions after loading first question with 30 seconds delay`() {
        coroutinesRule.runBlockingTest {
            `when`(realRepository.getBuff(ArgumentMatchers.anyInt())).then { fakeRepository.simpleFakeBuff() }
            viewModel = BuffViewModel(realRepository)
            viewModel.buffViewData.observeForever(buffDataObserver)
            delay(30_000)
            viewModel.onQuestionTimeFinished()
            verify(buffDataObserver, times(2)).onChanged(fakeRepository.simpleFakeBuff())
            delay(60_000)
            viewModel.onQuestionTimeFinished()
            verify(buffDataObserver, times(3)).onChanged(fakeRepository.simpleFakeBuff())
            delay(90_000)
            viewModel.onQuestionTimeFinished()
            verify(buffDataObserver, times(4)).onChanged(fakeRepository.simpleFakeBuff())
            delay(120_000)
            viewModel.onQuestionTimeFinished()
            verify(buffDataObserver, times(5)).onChanged(fakeRepository.simpleFakeBuff())
            delay(150_000)
            viewModel.onQuestionTimeFinished()
            verify(buffDataObserver, times(5)).onChanged(fakeRepository.simpleFakeBuff())
            delay(200_000)
            viewModel.onQuestionTimeFinished()
            verify(buffDataObserver, times(5)).onChanged(fakeRepository.simpleFakeBuff())
            viewModel.buffViewData.removeObserver(buffDataObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `viewModel listens to timer to hide the current question`() {
        coroutinesRule.runBlockingTest {
            `when`(realRepository.getBuff(ArgumentMatchers.anyInt())).then { fakeRepository.simpleFakeBuff() }
            viewModel = BuffViewModel(realRepository)
            viewModel.hideBuffViewData.observeForever(hideBuffDataObserver)
            viewModel.onQuestionTimeFinished()
            verify(hideBuffDataObserver).onChanged(Unit)
            viewModel.buffViewData.removeObserver(buffDataObserver)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `viewModel listens to timer to show next question`() {
        coroutinesRule.runBlockingTest {
            `when`(realRepository.getBuff(ArgumentMatchers.anyInt())).then { fakeRepository.simpleFakeBuff() }
            viewModel = BuffViewModel(realRepository)
            viewModel.buffViewData.observeForever(buffDataObserver)
            viewModel.onQuestionTimeFinished()
            verify(
                buffDataObserver,
                times(viewModel.currentQuestion)
            ).onChanged(fakeRepository.simpleFakeBuff())
            viewModel.buffViewData.removeObserver(buffDataObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `on submitting answers viewModel freeze the question for 2 sec and shows the next question`() {
        coroutinesRule.runBlockingTest {
            `when`(realRepository.getBuff(ArgumentMatchers.anyInt())).then { fakeRepository.simpleFakeBuff() }
            viewModel = BuffViewModel(realRepository)
            val currentSelectedQuestion = viewModel.currentQuestion
            viewModel.buffViewData.observeForever(buffDataObserver)
            viewModel.submitAnswer(fakeRepository.simpleFakeBuff().answers[1])
            verify(buffDataObserver, times(currentSelectedQuestion))
            delay(1_000)
            verify(buffDataObserver, times(currentSelectedQuestion))
            delay(2_000)
            verify(buffDataObserver, times(currentSelectedQuestion + 1))
            viewModel.buffViewData.removeObserver(buffDataObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `on closing buff viewModel hide the buff`() {
        coroutinesRule.runBlockingTest {
            `when`(realRepository.getBuff(ArgumentMatchers.anyInt())).then { fakeRepository.simpleFakeBuff() }
            viewModel = BuffViewModel(realRepository)
            viewModel.close()
            verify(hideBuffDataObserver).onChanged(Unit)
        }
    }

    @Test
    fun `load next question if it is less than 6`() {

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