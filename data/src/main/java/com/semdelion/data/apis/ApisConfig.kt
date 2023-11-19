<<<<<<<< HEAD:app/src/main/java/com/semdelion/presentation/di/NetworkModule.kt
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
========
package com.semdelion.data.apis

import android.content.Context
import com.semdelion.data.core.apis.client.ApiClient
import com.semdelion.data.core.apis.client.intercepters.LoggingInterceptor
import com.semdelion.data.core.apis.client.intercepters.NetworkConnectionInterceptor
import com.semdelion.data.core.apis.client.intercepters.NewsAuthInterceptor
>>>>>>>> master:data/src/main/java/com/semdelion/data/apis/ApisConfig.kt
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

<<<<<<<< HEAD:app/src/main/java/com/semdelion/presentation/di/NetworkModule.kt
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
========
object ApisConfig {
    private lateinit var applicationContext: Context
>>>>>>>> master:data/src/main/java/com/semdelion/data/apis/ApisConfig.kt

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