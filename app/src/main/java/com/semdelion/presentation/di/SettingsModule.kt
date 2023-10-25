package com.semdelion.presentation.di

import com.semdelion.data.storages.account.AppSettings
import com.semdelion.data.storages.account.SharedPreferencesAppSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    @Singleton
    abstract fun bindAppSettings(
        appSettings: SharedPreferencesAppSettings
    ): AppSettings
}

