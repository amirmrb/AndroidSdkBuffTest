package com.buffup.buffsdk.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.buffup.buffsdk.model.view.AnswerData
import com.buffup.buffsdk.model.view.BuffViewData
import com.buffup.buffsdk.repo.BuffRepository
import com.buffup.buffsdk.repo.FakeBuffRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    lateinit var answerSelectedObserver: Observer<AnswerData?>

    @Mock
    lateinit var realRepository: BuffRepository
    private val fakeRepository = FakeBuffRepository()

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
            viewModel.initialize()
            viewModel.buffViewData.observeForever(buffDataObserver)
            advanceTimeBy(30_000)
            viewModel.onQuestionTimeFinished()
            verify(buffDataObserver, times(2)).onChanged(fakeRepository.simpleFakeBuff())
            advanceTimeBy(60_000)
            viewModel.onQuestionTimeFinished()
            verify(buffDataObserver, times(3)).onChanged(fakeRepository.simpleFakeBuff())
            advanceTimeBy(90_000)
            viewModel.onQuestionTimeFinished()
            verify(buffDataObserver, times(4)).onChanged(fakeRepository.simpleFakeBuff())
            advanceTimeBy(120_000)
            viewModel.onQuestionTimeFinished()
            verify(buffDataObserver, times(5)).onChanged(fakeRepository.simpleFakeBuff())
            advanceTimeBy(150_000)
            viewModel.onQuestionTimeFinished()
            verify(buffDataObserver, times(5)).onChanged(fakeRepository.simpleFakeBuff())
            advanceTimeBy(200_000)
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
        val answer = fakeRepository.simpleFakeBuff().answers[1]
        coroutinesRule.runBlockingTest {
            `when`(realRepository.getBuff(ArgumentMatchers.anyInt())).then { fakeRepository.simpleFakeBuff() }
            viewModel = BuffViewModel(realRepository)
            viewModel.initialize()
            val currentSelectedQuestion = viewModel.currentQuestion
            viewModel.buffViewData.observeForever(buffDataObserver)
            viewModel.hideBuffViewData.observeForever(hideBuffDataObserver)
            verify(
                buffDataObserver,
                times(currentSelectedQuestion)
            ).onChanged(fakeRepository.simpleFakeBuff())
            viewModel.submitAnswer(answer)
            advanceTimeBy(1_000)
            verify(buffDataObserver, times(1)).onChanged(fakeRepository.simpleFakeBuff())
            advanceTimeBy(2_000)
            verify(hideBuffDataObserver, atLeastOnce()).onChanged(Unit)
            verify(buffDataObserver, times(2)).onChanged(fakeRepository.simpleFakeBuff())
            viewModel.buffViewData.removeObserver(buffDataObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `on submitting answers selected answer ui should change`() {
        val answer = fakeRepository.simpleFakeBuff().answers[1]
        coroutinesRule.runBlockingTest {
            `when`(realRepository.getBuff(ArgumentMatchers.anyInt())).then { fakeRepository.simpleFakeBuff() }
            viewModel = BuffViewModel(realRepository)
            viewModel.initialize()
            viewModel.answerSelectedLiveData.observeForever(answerSelectedObserver)
            viewModel.submitAnswer(answer)
            verify(answerSelectedObserver, atLeastOnce()).onChanged(answer)
            viewModel.answerSelectedLiveData.removeObserver(answerSelectedObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `on closing buff viewModel hide the buff`() {
        coroutinesRule.runBlockingTest {
            viewModel = BuffViewModel(realRepository)
            viewModel.hideBuffViewData.observeForever(hideBuffDataObserver)
            viewModel.close()
            verify(hideBuffDataObserver, atLeastOnce()).onChanged(Unit)
            viewModel.hideBuffViewData.removeObserver(hideBuffDataObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `load next question if it is less than 6`() {
        coroutinesRule.runBlockingTest {
            `when`(realRepository.getBuff(ArgumentMatchers.anyInt())).then { fakeRepository.simpleFakeBuff() }
            viewModel = BuffViewModel(realRepository)
            viewModel.buffViewData.observeForever(buffDataObserver)
            for (i in 0..10) {
                viewModel.loadNextQuestion()
            }
            verify(buffDataObserver, times(5)).onChanged(fakeRepository.simpleFakeBuff())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `submit answer should disable answering for 2 sec`() {
        val answer = fakeRepository.simpleFakeBuff().answers[1]
        val answer2 = fakeRepository.simpleFakeBuff().answers[2]
        coroutinesRule.runBlockingTest {
            `when`(realRepository.getBuff(ArgumentMatchers.anyInt())).then { fakeRepository.simpleFakeBuff() }
            viewModel = BuffViewModel(realRepository)
            viewModel.answerSelectedLiveData.observeForever(answerSelectedObserver)
            viewModel.initialize()
            viewModel.submitAnswer(answer)
            viewModel.submitAnswer(answer2)
            verify(answerSelectedObserver, times(1)).onChanged(answer)
            viewModel.answerSelectedLiveData.removeObserver(answerSelectedObserver)
        }
    }
}