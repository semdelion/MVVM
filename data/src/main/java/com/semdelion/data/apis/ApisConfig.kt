package com.semdelion.data.apis

import android.content.Context
import com.semdelion.data.core.apis.client.ApiClient
import com.semdelion.data.core.apis.client.intercepters.LoggingInterceptor
import com.semdelion.data.core.apis.client.intercepters.NetworkConnectionInterceptor
import com.semdelion.data.core.apis.client.intercepters.NewsAuthInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApisConfig {
    private lateinit var applicationContext: Context

    private lateinit var baseUrl: String

    val httpClient: ApiClient by lazy {
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        okHttpClient.addInterceptor(LoggingInterceptor.defaultInterceptor())
        okHttpClient.addInterceptor(NewsAuthInterceptor())
        okHttpClient.addInterceptor(NetworkConnectionInterceptor(applicationContext))


        ApiClient(baseUrl, okHttpClient)
    }

    fun init(context: Context, baseUrl: String = "https://newsdata.io/api/") {
        this.baseUrl = baseUrl
        applicationContext = context
    }
}