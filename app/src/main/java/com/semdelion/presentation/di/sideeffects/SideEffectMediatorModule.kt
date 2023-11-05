package com.semdelion.presentation.di.sideeffects

import android.content.Context
import com.semdelion.presentation.core.sideeffects.dialogs.Dialogs
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogsSideEffectMediator
import com.semdelion.presentation.core.sideeffects.intents.Intents
import com.semdelion.presentation.core.sideeffects.intents.plugin.IntentsSideEffectMediator
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.navigator.plugin.NavigatorSideEffectMediator
import com.semdelion.presentation.core.sideeffects.permissions.Permissions
import com.semdelion.presentation.core.sideeffects.permissions.plugin.PermissionsSideEffectMediator
import com.semdelion.presentation.core.sideeffects.resources.Resources
import com.semdelion.presentation.core.sideeffects.resources.plugin.ResourcesSideEffectMediator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.sideeffects.toasts.plugin.ToastsSideEffectMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SideEffectMediatorModule {

    @Provides
    @Singleton
    fun provideToastsPlugin(@ApplicationContext context: Context): Toasts {
        return  ToastsSideEffectMediator(context)

    }

    @Provides
    @Singleton
    fun provideDialogs(): Dialogs {
        return DialogsSideEffectMediator()
    }

    @Provides
    @Singleton
    fun provideIntents(@ApplicationContext context: Context): Intents {
        return  IntentsSideEffectMediator(context)
    }

    @Provides
    @Singleton
    fun provideNavigator(): Navigator {
        return NavigatorSideEffectMediator()
    }

    @Provides
    @Singleton
    fun providePermissionsPlugin(@ApplicationContext context: Context): Permissions {
        return PermissionsSideEffectMediator(context)
    }

    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context): Resources {
        return  ResourcesSideEffectMediator(context)
    }
}