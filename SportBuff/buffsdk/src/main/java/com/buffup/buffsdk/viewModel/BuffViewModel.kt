package com.buffup.buffsdk.viewModel

import androidx.lifecycle.MutableLiveData
import com.buffup.buffsdk.IQuestionOverVideo
import com.buffup.buffsdk.IdealStatus
import com.buffup.buffsdk.Status
import com.buffup.buffsdk.model.response.Answer
import com.buffup.buffsdk.model.view.BuffViewData
import com.buffup.buffsdk.repo.BuffRepository


class BuffViewModel(private val repository: BuffRepository) : BaseViewModel(),
    IQuestionOverVideo<Answer> {

    val currentQuestion = 1
    val hideBuffViewData = MutableLiveData<Unit?>()
    val buffViewData = MutableLiveData<BuffViewData>().apply { }

    init {
    }

    override var status: Status = IdealStatus
    override fun init() {
        apiCall({ repository.getBuff(1) }, {
            buffViewData.value = it
        })
    }

    override fun loadQuestion(questionNumber: Int) {
        TODO("Not yet implemented")
    }

    override fun getRemainingTime(): Long {
        TODO("Not yet implemented")
    }

    override fun submitAnswer(answer: Answer) {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    fun onFinishedTime() {
        TODO("Not yet implemented")
    }


}