package com.semdelion.data.repositories

import com.semdelion.data.storages.interfaces.IMessageStorage
import com.semdelion.data.storages.models.MessageDataModel
import com.semdelion.data.storages.models.toMessageModel
import com.semdelion.domain.models.Message
import com.semdelion.domain.repositories.IMessageRepository
import kotlinx.coroutines.delay

class MessageRepositoryImpl(
    private val messageStorage: IMessageStorage,
) : IMessageRepository {
    override suspend fun saveMessage(message: Message): Boolean {
        delay(2000)
        return messageStorage.save(MessageDataModel(message.text))
    }

    override suspend fun getMessage(): Message {
        delay(2000)
        val dataModel = messageStorage.get()
        return dataModel.toMessageModel()
    }
}
