package com.semdelion.data.utils.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.semdelion.data.utils.fromJson

class ListTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return try {
            Gson().fromJson<List<String>>(value)
        } catch (e: Exception) {
            listOf()
        }
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)

    }
}