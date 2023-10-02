package com.semdelion.data.repositories

import com.semdelion.data.storages.user.IUserStorage
import com.semdelion.data.storages.user.UserDataModel
import com.semdelion.data.storages.user.toUserModel
import com.semdelion.domain.repositories.user.models.UserModel
import com.semdelion.domain.repositories.user.IUserRepository

class UserRepositoryImpl(private val userStorage: IUserStorage) : IUserRepository {

    override fun saveUser(userModel: UserModel): Boolean {
        val userDataModel = UserDataModel(
            firstName = userModel.firstName,
            lastName = userModel.lastName
        )
        return userStorage.save(userDataModel)
    }

    override fun getUser(): UserModel {
        val userDataModel = userStorage.get()
        return userDataModel.toUserModel()
    }
}