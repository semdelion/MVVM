package com.semdelion.presentation.di.sideeffects

import com.semdelion.presentation.R
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogsPlugin
import com.semdelion.presentation.core.sideeffects.intents.plugin.IntentsPlugin
import com.semdelion.presentation.core.sideeffects.navigator.plugin.NavigatorPlugin
import com.semdelion.presentation.core.sideeffects.navigator.plugin.StackFragmentNavigator
import com.semdelion.presentation.core.sideeffects.permissions.plugin.PermissionsPlugin
import com.semdelion.presentation.core.sideeffects.resources.plugin.ResourcesPlugin
import com.semdelion.presentation.core.sideeffects.toasts.plugin.ToastsPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SideEffectPluginsModule {

    @Provides
    @Singleton
    fun provideIntentsPlugin(): IntentsPlugin {
        return IntentsPlugin()
    }

    @Provides
    @Singleton
    fun provideToastsPlugin(): ToastsPlugin {
        return ToastsPlugin()
    }

    @Provides
    @Singleton
    fun provideResourcesPlugin(): ResourcesPlugin {
        return ResourcesPlugin()
    }

    @Provides
    @Singleton
    fun provideDialogsPlugin(): DialogsPlugin {
        return DialogsPlugin()
    }

    @Provides
    @Singleton
    fun providePermissionsPlugin(): PermissionsPlugin {
        return PermissionsPlugin()
    }

    //TODO _Hilt тут косяк и его нужно править, я бы добавил в базовую активти createNavigator и тянул бы там
    @Provides
    @Singleton
    fun provideNavigatorPlugin(): NavigatorPlugin {

        val createNavigator = StackFragmentNavigator(
            containersId = setOf(R.id.fragment_container, R.id.tabs_fragment_container),
            topLevelDestinationsId = setOf(R.id.tabsFragment, R.id.signInFragment),
        )
        return NavigatorPlugin(createNavigator)
    }
}