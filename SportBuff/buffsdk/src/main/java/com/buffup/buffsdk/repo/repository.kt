package com.buffup.buffsdk.repo

class repository {
    suspend fun getBuff(buffId: Int) = RetrofitProvider.getRemoteService().getBuff(buffId)
}