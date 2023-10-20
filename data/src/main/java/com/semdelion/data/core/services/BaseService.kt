package com.semdelion.data.core.services

import com.semdelion.data.core.services.client.ApiClient

abstract class BaseService(private val apiClient: ApiClient) {

    /*suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
        } catch () {

        }
    }*/
}