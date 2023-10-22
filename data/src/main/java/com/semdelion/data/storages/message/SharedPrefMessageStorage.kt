package com.semdelion.data.storages.message

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val SHARED_NAME_USER_DB = "shared_preferences_bd"
private const val USER_TEST = "message_test"

class SharedPrefMessageStorage(context: Context) : IMessageStorage {
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_NAME_USER_DB, Context.MODE_PRIVATE)

    override fun save(message: MessageDataModel): Boolean {
        try {
            val json = Json.encodeToString(message)
            sharedPreferences.edit().putString(USER_TEST, json).apply()
        } catch (ex: Exception) {
            return false
            //TODO add error logs
        }

        return true
    }

    override fun get(): MessageDataModel {
        val jsonUser = sharedPreferences.getString(USER_TEST, "")
        var message = MessageDataModel("")
        jsonUser?.let {
            try {
                message = Json.decodeFromString(it)
            } catch (ex: Exception) {
                //TODO add error logs
            }
        }
        return message
    }
}