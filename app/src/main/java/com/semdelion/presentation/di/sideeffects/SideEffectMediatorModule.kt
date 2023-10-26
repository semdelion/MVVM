package com.semdelion.presentation.di.sideeffects

import android.content.Context
import com.semdelion.presentation.core.sideeffects.dialogs.Dialogs
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogsPlugin
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogsSideEffectMediator
import com.semdelion.presentation.core.sideeffects.intents.Intents
import com.semdelion.presentation.core.sideeffects.intents.plugin.IntentsPlugin
import com.semdelion.presentation.core.sideeffects.intents.plugin.IntentsSideEffectMediator
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.navigator.plugin.NavigatorPlugin
import com.semdelion.presentation.core.sideeffects.navigator.plugin.NavigatorSideEffectMediator
import com.semdelion.presentation.core.sideeffects.permissions.Permissions
import com.semdelion.presentation.core.sideeffects.permissions.plugin.PermissionsPlugin
import com.semdelion.presentation.core.sideeffects.permissions.plugin.PermissionsSideEffectMediator
import com.semdelion.presentation.core.sideeffects.resources.Resources
import com.semdelion.presentation.core.sideeffects.resources.plugin.ResourcesPlugin
import com.semdelion.presentation.core.sideeffects.resources.plugin.ResourcesSideEffectMediator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.sideeffects.toasts.plugin.ToastsPlugin
import com.semdelion.presentation.core.sideeffects.toasts.plugin.ToastsSideEffectMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SideEffectMediatorModule {

    @Provides
    @Singleton
    fun provideToastsPluginMediator(@ApplicationContext context: Context, toastsPlugin: ToastsPlugin): Toasts {
        return  toastsPlugin.createMediator(context) as ToastsSideEffectMediator
    }

    @Provides
    @Singleton
    fun provideDialogsMediator(@ApplicationContext context: Context, dialogsPlugin: DialogsPlugin): Dialogs {
        return  dialogsPlugin.createMediator(context) as DialogsSideEffectMediator
    }

    @Provides
    @Singleton
    fun provideIntentsMediator(@ApplicationContext context: Context, intentsPlugin: IntentsPlugin): Intents {
        return  intentsPlugin.createMediator(context) as IntentsSideEffectMediator
    }

    @Provides
    @Singleton
    fun provideNavigatorMediator(@ApplicationContext context: Context, navigatorPlugin: NavigatorPlugin): Navigator {
        return  navigatorPlugin.createMediator(context) as NavigatorSideEffectMediator
    }

    @Provides
    @Singleton
    fun providePermissionsPluginMediator(@ApplicationContext context: Context, permissionsPlugin: PermissionsPlugin): Permissions {
        return  permissionsPlugin.createMediator(context) as PermissionsSideEffectMediator
    }

    @Provides
    @Singleton
    fun provideResourcesMediator(@ApplicationContext context: Context, resourcesPlugin: ResourcesPlugin): Resources {
        return  resourcesPlugin.createMediator(context) as ResourcesSideEffectMediator
    }
}