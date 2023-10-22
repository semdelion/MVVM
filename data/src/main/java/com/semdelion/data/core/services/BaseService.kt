package com.semdelion.data.core.services

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension

import com.semdelion.data.core.services.client.ApiClient
import com.semdelion.data.core.services.exceptions.NoConnectivityException
import com.semdelion.data.core.services.exceptions.ParseBackendResponseException
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import java.io.IOException

abstract class BaseService(private val apiClient: ApiClient, private val dispatcher: IoDispatcher) {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun <T> wrapRetrofitExceptions(block: suspend CoroutineScope.() -> T): T {
        return try {
            withContext(dispatcher.value, block)
        } catch (ex: JsonDataException) {
            throw ParseBackendResponseException(ex)
        } catch (ex: JsonEncodingException) {
            throw ParseBackendResponseException(ex)
        } catch (ex: HttpException) {
            throw ParseBackendResponseException(ex)
        } catch (ex: NoConnectivityException) {
            throw ex
        } catch (ex:IOException) {
            throw ex
        }
    }
}