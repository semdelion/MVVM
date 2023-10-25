package com.semdelion.presentation.di

import android.content.Context
import com.semdelion.data.core.services.client.ApiClient
import com.semdelion.data.core.services.client.intercepters.LoggingInterceptor
import com.semdelion.data.core.services.client.intercepters.NetworkConnectionInterceptor
import com.semdelion.data.core.services.client.intercepters.NewsAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideAppClient(@ApplicationContext context: Context) : ApiClient {

        val okHttpClient = OkHttpClient()
            .newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        okHttpClient.addInterceptor(LoggingInterceptor.defaultInterceptor())
        okHttpClient.addInterceptor(NewsAuthInterceptor())
        okHttpClient.addInterceptor(NetworkConnectionInterceptor(context))

        return ApiClient("https://newsdata.io/api/", okHttpClient)
    }
}