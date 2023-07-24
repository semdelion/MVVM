package com.semdelion.domain.repositories

import com.semdelion.domain.core.tasks.Task
import com.semdelion.domain.models.Message

interface IMessageRepository : IRepository {
    fun saveMessage(message: Message): Task<Boolean>
    fun getMessage(): Task<Message>
}