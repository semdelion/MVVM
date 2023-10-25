package com.semdelion.data.repositories

import com.semdelion.data.storages.message.IMessageStorage
import com.semdelion.data.storages.message.MessageDataModel
import com.semdelion.data.storages.message.toMessageModel
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.repositories.message.models.Message
import com.semdelion.domain.repositories.message.IMessageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepositoryImpl @Inject constructor(
    private val messageStorage: IMessageStorage,
    private val ioDispatcher: IoDispatcher
) : IMessageRepository {
    override suspend fun saveMessage(message: Message): Boolean = withContext(ioDispatcher.value) {
        return@withContext messageStorage.save(MessageDataModel(message.text))
    }

    override suspend fun getMessage(): Message = withContext(ioDispatcher.value) {
        delay(1000)
        val dataModel = messageStorage.get()
        return@withContext dataModel.toMessageModel()
    }
}
