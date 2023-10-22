package com.semdelion.data.storages.message

interface IMessageStorage {
    fun save(message: MessageDataModel): Boolean
    fun get(): MessageDataModel
}