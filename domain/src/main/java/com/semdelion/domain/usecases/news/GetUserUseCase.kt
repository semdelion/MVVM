package com.semdelion.domain.usecases.news

import com.semdelion.domain.models.UserModel
import com.semdelion.domain.repositories.IUserRepository

class GetUserUseCase(private val repository: IUserRepository) {
    fun execute(): UserModel {
        return repository.getUser()
    }
}