package com.buffup.buffsdk.viewModel

import androidx.lifecycle.MutableLiveData
import com.buffup.buffsdk.IBuff
import com.buffup.buffsdk.model.view.AnswerData
import com.buffup.buffsdk.model.view.BuffViewData
import com.buffup.buffsdk.repo.BuffRepository
import com.buffup.buffsdk.utils.clear
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


class BuffViewModel(
    private val repository: BuffRepository
) : BaseViewModel(),
    IBuff<AnswerData> {

    var currentQuestion = 0
    val hideBuffViewData = MutableLiveData<Unit?>()
    val buffViewData = MutableLiveData<BuffViewData>().apply { }
    val answerSelectedLiveData = MutableLiveData<AnswerData?>().apply { value = null }

    override fun initialize() {
        loadNextQuestion()
    }

    override fun loadNextQuestion() {
        if (currentQuestion > 0)
            hideBuffViewData.value = Unit
        currentQuestion++
        if (currentQuestion <= 5) {
            apiCall({ repository.getBuff(currentQuestion) }, {
                buffViewData.value = it
            })
        }
    }

    override fun submitAnswer(answer: AnswerData) {
        // todo send answer to every where it should go
        if (answerSelectedLiveData.value == null) {
            answerSelectedLiveData.value = answer
            delayDo(2000) {
                loadNextQuestion()
                answerSelectedLiveData.clear()
            }
        }
    }

    override fun close() {
        hideBuffViewData.value = Unit
        loadNextQuestion()
    }

    override fun onQuestionTimeFinished() {
        hideBuffViewData.value = Unit
        loadNextQuestion()
    }


}