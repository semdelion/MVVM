package com.semdelion.data.storages.models
import com.semdelion.domain.models.Message
import kotlinx.serialization.Serializable

@Serializable
data class MessageDataModel(val text: String) {
}

fun MessageDataModel.toMessageModel(): Message {
    return Message(text = text)
}