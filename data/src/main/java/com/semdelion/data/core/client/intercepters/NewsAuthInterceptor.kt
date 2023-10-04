package com.semdelion.data.core.client.intercepters

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val NEW_KEY = "pub_21221020bb9c580281896a2305077b7ffbe0f"
class NewsAuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val request: Request = original.newBuilder()
            .header("X-ACCESS-KEY", NEW_KEY)
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}