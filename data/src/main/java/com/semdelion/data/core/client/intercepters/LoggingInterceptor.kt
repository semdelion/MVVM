package com.semdelion.data.core.client.intercepters

import com.semdelion.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor {
    companion object {

        fun defaultInterceptor():Interceptor {

         val interceptor = HttpLoggingInterceptor()
         if (BuildConfig.DEBUG) {
             interceptor.level = HttpLoggingInterceptor.Level.BODY
         }
         return interceptor
        }
    }
}