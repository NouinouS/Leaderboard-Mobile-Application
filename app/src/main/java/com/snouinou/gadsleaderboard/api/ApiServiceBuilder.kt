package com.snouinou.gadsleaderboard.api

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory


object ApiServiceBuilder {
    private const val URL = "https://gadsapi.herokuapp.com/"

    // Create OkHttp Client
    private val okHttp =
        OkHttpClient.Builder()
    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
    private val retrofit = builder.build()

    fun <S> buildApiService(serviceType: Class<S>?): S {
        return retrofit.create(serviceType)
    }
}
