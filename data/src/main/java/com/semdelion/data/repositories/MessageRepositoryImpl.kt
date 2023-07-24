package com.semdelion.data.repositories

import com.semdelion.data.storages.interfaces.IMessageStorage
import com.semdelion.data.storages.models.MessageDataModel
import com.semdelion.data.storages.models.toMessageModel
import com.semdelion.domain.core.tasks.Task
import com.semdelion.domain.core.tasks.TasksFactory
import com.semdelion.domain.core.tasks.ThreadUtils
import com.semdelion.domain.models.Message
import com.semdelion.domain.repositories.IMessageRepository

class MessageRepositoryImpl(
    private val messageStorage: IMessageStorage,
    private val tasksFactory: TasksFactory,
    private val threadUtils: ThreadUtils,
) : IMessageRepository {
    override fun saveMessage(message: Message): Task<Boolean> = tasksFactory.async {
        threadUtils.sleep(2000)
        return@async messageStorage.save(MessageDataModel(message.text))
    }

    override fun getMessage(): Task<Message> = tasksFactory.async  {
        threadUtils.sleep(2000)
        val dataModel = messageStorage.get()

        return@async dataModel.toMessageModel()
    }
}
