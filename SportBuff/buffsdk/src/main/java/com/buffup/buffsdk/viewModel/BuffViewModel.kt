package com.buffup.buffsdk.viewModel

import androidx.lifecycle.MutableLiveData
import com.buffup.buffsdk.IBuff
import com.buffup.buffsdk.model.response.Answer
import com.buffup.buffsdk.model.view.BuffViewData
import com.buffup.buffsdk.repo.BuffRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class BuffViewModel(private val repository: BuffRepository) : BaseViewModel(),
    IBuff<Answer> {

    var currentQuestion = 0
    val hideBuffViewData = MutableLiveData<Unit?>()
    val buffViewData = MutableLiveData<BuffViewData>().apply { }
    val answerSelectedLiveData = MutableLiveData<Answer?>().apply { value = null }

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

    override fun submitAnswer(answer: Answer) {
        // todo send answer to every where it should go
       runBlocking {
           delay(2000)
           loadNextQuestion()
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