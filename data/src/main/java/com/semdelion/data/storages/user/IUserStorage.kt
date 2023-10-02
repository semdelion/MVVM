package com.semdelion.data.storages.user

interface IUserStorage {
    fun save(user: UserDataModel): Boolean
    fun get(): UserDataModel
}