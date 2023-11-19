package com.semdelion.data.utils.room

import androidx.room.TypeConverter
import com.semdelion.data.core.apis.client.Serializer.moshi
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

inline fun <reified T> Moshi.listToJson(list: List<T>): String {
    return adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java)).toJson(list)
}
inline fun <reified T> Moshi.parseList(jsonString: String): List<T>? {
    return adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java)).fromJson(jsonString)
}

class ListTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return try {
            return moshi.parseList(value) ?: listOf()
        } catch (e: Exception) {
            listOf()
        }
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return moshi.listToJson(list)
    }
}