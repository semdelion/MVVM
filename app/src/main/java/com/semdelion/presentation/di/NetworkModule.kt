
package com.semdelion.presentation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.semdelion.data.core.apis.client.ApiClient
import com.semdelion.data.core.apis.client.intercepters.LoggingInterceptor
import com.semdelion.data.core.apis.client.intercepters.NetworkConnectionInterceptor
import com.semdelion.data.core.apis.client.intercepters.NewsAuthInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApisConfig {

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