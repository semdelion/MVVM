package com.semdelion.domain.repositories.message

import com.semdelion.domain.repositories.message.models.Message

interface IMessageRepository {
    suspend fun saveMessage(message: Message): Boolean
    suspend fun getMessage(): Message
}