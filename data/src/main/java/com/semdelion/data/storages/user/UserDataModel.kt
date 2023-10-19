package com.semdelion.data.storages.user

import com.semdelion.domain.repositories.user.models.UserModel
import kotlinx.serialization.Serializable

@Serializable
data class UserDataModel(val firstName: String, val lastName: String) {
    companion object { private const val serialVersionUID = 3L }
}

fun UserDataModel.toUserModel(): UserModel {
    return UserModel(firstName = firstName, lastName = lastName)
}