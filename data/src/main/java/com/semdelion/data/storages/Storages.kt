package com.semdelion.data.storages

import android.content.Context
import androidx.room.Room
import com.semdelion.data.storages.account.AccountRoomStorage
import com.semdelion.data.storages.account.IAccountsStorage
import com.semdelion.data.storages.news.IFavoriteNewsStorage
import com.semdelion.data.storages.news.RoomFavoriteNewsStorage

object Storages {
    private lateinit var applicationContext: Context

    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db").build()
    }

    val favoriteNewsStorage by lazy<IFavoriteNewsStorage> {
        RoomFavoriteNewsStorage(appDatabase.getFavoriteNewsDao())
    }

    val accountsStorage by lazy<IAccountsStorage> {
        AccountRoomStorage(appDatabase.getAccountsDao())
    }

    fun init(context:Context) {
        applicationContext = context
    }
}