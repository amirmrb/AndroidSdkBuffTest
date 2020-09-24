package com.buffup.buffsdk.repo

import com.buffup.buffsdk.model.view.BuffViewData

class BuffRepository {
    suspend fun getBuff(buffId: Int) =
        RetrofitProvider.getRemoteService().getBuff(buffId).result.run {
            BuffViewData(this.answers, this.author, this.id, this.question, this.time_to_show)
        }
}