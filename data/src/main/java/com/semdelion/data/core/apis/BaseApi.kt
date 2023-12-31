package com.semdelion.data.core.apis

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.semdelion.data.core.apis.client.ApiClient
import com.semdelion.data.core.apis.exceptions.NoConnectivityException
import com.semdelion.data.core.apis.exceptions.ParseBackendResponseException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import java.io.IOException

abstract class BaseApi(private val apiClient: ApiClient) {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun <T> wrapRetrofitExceptions(dispatcher: CoroutineDispatcher, block: suspend CoroutineScope.() -> T): T {
        return try {
            withContext(dispatcher, block)
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