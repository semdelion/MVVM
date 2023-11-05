package com.semdelion.presentation.core.sideeffects.intents.plugin

import android.content.Context
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.SideEffectPlugin
import com.semdelion.presentation.core.sideeffects.SideEffectProvider
import com.semdelion.presentation.core.sideeffects.intents.Intents
import dagger.hilt.android.EntryPointAccessors

/**
 * Plugin for launching system activities from view-models.
 * Allows adding [Intents] interface to the view-model constructor.
 */
class IntentsPlugin : SideEffectPlugin<Intents, Nothing> {

    override val mediatorClass: Class<Intents>
        get() = Intents::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<Nothing> {
        val sideEffectProvider = EntryPointAccessors.fromApplication(applicationContext, SideEffectProvider::class.java)
        return sideEffectProvider.getIntents() as SideEffectMediator<Nothing>
    }
}