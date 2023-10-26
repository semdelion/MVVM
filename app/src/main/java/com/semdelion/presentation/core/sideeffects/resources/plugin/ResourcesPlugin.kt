package com.semdelion.presentation.core.sideeffects.resources.plugin

import android.content.Context
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.SideEffectPlugin
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogsSideEffectMediator

class ResourcesPlugin : SideEffectPlugin<ResourcesSideEffectMediator, Nothing> {

    override val mediatorClass: Class<ResourcesSideEffectMediator>
        get() = ResourcesSideEffectMediator::class.java

    private var current: SideEffectMediator<Nothing>? = null

    override fun createMediator(applicationContext: Context): SideEffectMediator<Nothing> {
        if(current == null)
            current = ResourcesSideEffectMediator(applicationContext)
        return current!!
    }

}