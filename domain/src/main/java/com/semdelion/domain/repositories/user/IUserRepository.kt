package com.semdelion.domain.repositories.user

import com.semdelion.domain.repositories.user.models.UserModel

interface IUserRepository {
    fun saveUser(userModel: UserModel): Boolean
    fun getUser(): UserModel
}