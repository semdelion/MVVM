package com.semdelion.domain.repositories

import com.semdelion.domain.core.tasks.Task
import com.semdelion.domain.models.Message

interface IMessageRepository : IRepository {
    suspend fun saveMessage(message: Message): Boolean
    suspend fun getMessage(): Message
}