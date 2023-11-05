package com.semdelion.presentation.core.sideeffects

import com.semdelion.presentation.core.sideeffects.dialogs.Dialogs
import com.semdelion.presentation.core.sideeffects.intents.Intents
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.permissions.Permissions
import com.semdelion.presentation.core.sideeffects.resources.Resources
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SideEffectProvider {
    fun getToasts(): Toasts
    fun getResources(): Resources
    fun getPermissions(): Permissions
    fun getNavigator(): Navigator
    fun getIntents(): Intents
    fun getDialogs(): Dialogs
}