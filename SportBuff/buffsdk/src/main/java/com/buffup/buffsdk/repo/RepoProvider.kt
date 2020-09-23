package com.buffup.buffsdk.repo

import com.buffup.sdk.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RepoProvider {
    private fun getRemoteService(): RemoteService {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val headerAuthorizationInterceptor = Interceptor { chain ->
            var request = chain.request()
            val headers = request.headers().newBuilder().apply {
                add("Accept", "application/json")
                add("Content-Type", "application/x-www-form-urlencoded")
            }
            request = request.newBuilder().headers(headers.build()).build()
            chain.proceed(request)
        }

        val clientBuilder = OkHttpClient.Builder().apply {
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
            addInterceptor(headerAuthorizationInterceptor)
            if (BuildConfig.DEBUG) addInterceptor(logging)
        }
        //Added to auto convert underline_convention to camelCase to avoid using SerializedName annotation
        val gson = GsonBuilder().serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clientBuilder.build())
            .build()
        return retrofit.create(RemoteService::class.java)
    }
}