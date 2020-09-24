package com.buffup.buffsdk.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.buffup.buffsdk.IQuestionOverVideo
import com.buffup.buffsdk.IdealStatus
import com.buffup.buffsdk.Status
import com.buffup.buffsdk.model.response.Answer
import com.buffup.buffsdk.model.view.BuffViewData
import com.buffup.buffsdk.repo.BuffRepository
import com.buffup.sdk.BuildConfig


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
        currentQuestion ++
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
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun onQuestionTimeFinished() {
        hideBuffViewData.value = Unit
        loadNextQuestion()
    }


}