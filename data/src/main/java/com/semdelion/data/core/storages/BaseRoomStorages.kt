package com.semdelion.data.core.storages

import android.database.sqlite.SQLiteException
import com.semdelion.domain.repositories.accounts.StorageException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

abstract class BaseRoomStorages {

    suspend fun <T> wrapSQLiteException(dispatcher: CoroutineDispatcher, block: suspend CoroutineScope.() -> T): T {
        try {
            return withContext(dispatcher, block)
        } catch (e: SQLiteException) {
            val appException = StorageException()
            appException.initCause(e)
            throw appException
        }
    }
}