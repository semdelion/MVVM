package com.semdelion.data.storages.message
import com.semdelion.domain.repositories.message.models.Message
import kotlinx.serialization.Serializable

@Serializable
data class MessageDataModel(val text: String) {
    companion object { private const val serialVersionUID = 3L }
}

fun MessageDataModel.toMessageModel(): Message {
    return Message(text = text)
}