package com.semdelion.data.storages.interfaces

import com.semdelion.data.storages.models.MessageDataModel

interface IMessageStorage {
    fun save(message: MessageDataModel): Boolean
    fun get(): MessageDataModel
}