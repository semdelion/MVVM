package com.semdelion.domain.usecases.user

import com.semdelion.domain.repositories.user.models.UserModel
import com.semdelion.domain.repositories.user.IUserRepository

class GetUserUseCase(private val repository: IUserRepository) {
    fun execute(): UserModel {
        return repository.getUser()
    }
}