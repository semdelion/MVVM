package com.semdelion.presentation.core.sideeffects.resources.plugin

import android.content.Context
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.SideEffectPlugin
import com.semdelion.presentation.core.sideeffects.SideEffectProvider
import dagger.hilt.android.EntryPointAccessors

class ResourcesPlugin : SideEffectPlugin<ResourcesSideEffectMediator, Nothing> {

    override val mediatorClass: Class<ResourcesSideEffectMediator>
        get() = ResourcesSideEffectMediator::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<Nothing> {
        val sideEffectProvider = EntryPointAccessors.fromApplication(applicationContext, SideEffectProvider::class.java)
        return sideEffectProvider.getResources() as SideEffectMediator<Nothing>
    }
}