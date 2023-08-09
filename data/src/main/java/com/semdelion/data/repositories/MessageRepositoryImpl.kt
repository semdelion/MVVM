package com.semdelion.data.repositories

import com.semdelion.data.storages.interfaces.IMessageStorage
import com.semdelion.data.storages.models.MessageDataModel
import com.semdelion.data.storages.models.toMessageModel
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.models.Message
import com.semdelion.domain.repositories.IMessageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MessageRepositoryImpl(
    private val messageStorage: IMessageStorage,
    private val ioDispatcher: IoDispatcher
) : IMessageRepository {
    override suspend fun saveMessage(message: Message): Boolean = withContext(ioDispatcher.value) {
        delay(2000)
        return@withContext messageStorage.save(MessageDataModel(message.text))
    }

    override suspend fun getMessage(): Message = withContext(ioDispatcher.value) {
        delay(2000)
        val dataModel = messageStorage.get()
        return@withContext dataModel.toMessageModel()
    }
}
