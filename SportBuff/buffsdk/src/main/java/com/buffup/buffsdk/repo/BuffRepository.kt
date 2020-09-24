package com.buffup.buffsdk.repo

class Repository {
    suspend fun getBuff(buffId: Int) = RetrofitProvider.getRemoteService().getBuff(buffId)
}