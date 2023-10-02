package com.semdelion.data.storages

import androidx.room.Database
import androidx.room.RoomDatabase
import com.semdelion.data.storages.news.FavoriteNewsDao
import com.semdelion.data.storages.news.entities.FavoriteNewsEntity

@Database(
    version = 1,
    entities = [
        FavoriteNewsEntity::class
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getFavoriteNewsDao(): FavoriteNewsDao
}