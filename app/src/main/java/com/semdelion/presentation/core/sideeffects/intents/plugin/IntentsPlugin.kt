package com.semdelion.presentation.core.sideeffects.intents.plugin

import android.content.Context
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.SideEffectPlugin
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogsSideEffectImpl
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogsSideEffectMediator
import com.semdelion.presentation.core.sideeffects.intents.Intents

/**
 * Plugin for launching system activities from view-models.
 * Allows adding [Intents] interface to the view-model constructor.
 */
class IntentsPlugin : SideEffectPlugin<Intents, Nothing> {

    override val mediatorClass: Class<Intents>
        get() = Intents::class.java

    private var current: SideEffectMediator<Nothing>? = null

    override fun createMediator(applicationContext: Context): SideEffectMediator<Nothing> {
        if(current == null)
            current = IntentsSideEffectMediator(applicationContext)
        return current!!
    }

}