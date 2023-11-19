package com.semdelion.data.core.apis.client.intercepters

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.semdelion.data.core.apis.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetworkConnectionInterceptor(private val _context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectivityException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private fun isConnected(): Boolean {
        val result: Boolean
        val connectivityManager =
            _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

        return result
    }
}