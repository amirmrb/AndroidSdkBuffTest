package com.buffup.buffsdk.viewModel

import androidx.lifecycle.MutableLiveData
import com.buffup.buffsdk.IQuestionOverVideo
import com.buffup.buffsdk.IdealStatus
import com.buffup.buffsdk.Status
import com.buffup.buffsdk.model.response.Answer
import com.buffup.buffsdk.model.view.BuffViewData
import com.buffup.buffsdk.repo.BuffRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class BuffViewModel(private val repository: BuffRepository) : BaseViewModel(),
    IQuestionOverVideo<Answer> {

    var currentQuestion = 0
    val hideBuffViewData = MutableLiveData<Unit?>()
    val buffViewData = MutableLiveData<BuffViewData>().apply { }

    init {
        initialize()
    }

    override var status: Status = IdealStatus
    override fun initialize() {
        loadNextQuestion()
    }

    override fun loadNextQuestion() {
        hideBuffViewData.value = Unit
        currentQuestion++
        if (currentQuestion <= 5) {
            apiCall({ repository.getBuff(currentQuestion) }, {
                buffViewData.value = it
            })
        }
    }

    override fun getRemainingTime(): Long {
        TODO("Not yet implemented")
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
    }

    override fun onQuestionTimeFinished() {
        hideBuffViewData.value = Unit
        loadNextQuestion()
    }


}