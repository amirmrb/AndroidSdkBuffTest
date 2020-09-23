package com.buffup.buffsdk.repo

import com.buffup.buffsdk.model.response.BuffResponse
import com.buffup.buffsdk.model.response.Result
import com.buffup.buffsdk.utils.PRE_REQUEST_URL
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteService {
    @GET("${PRE_REQUEST_URL}/buffs/{buffId}")
    suspend fun getBuff(@Path("buffId") buffId: Int): BuffResponse<Result>
}