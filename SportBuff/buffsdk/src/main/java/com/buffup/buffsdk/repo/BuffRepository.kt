package com.buffup.buffsdk.repo

import com.buffup.buffsdk.model.view.AnswerData
import com.buffup.buffsdk.model.view.AuthorData
import com.buffup.buffsdk.model.view.BuffViewData

class BuffRepository {
    suspend fun getBuff(buffId: Int) =
        RetrofitProvider.getRemoteService().getBuff(buffId).result.run {
            BuffViewData(
                this.answers.map { AnswerData(it) },
                AuthorData("${this.author.firstName} ${this.author.lastName}", author.image),
                this.id,
                this.question,
                this.timeToShow
            )
        }
}