package com.semdelion.presentation.core.sideeffects.toasts.plugin

import android.content.Context
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.SideEffectPlugin
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogsSideEffectMediator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts

/**
 * Plugin for displaying toast messages from view-models.
 * Allows adding [Toasts] interface to the view-model constructor.
 */
class ToastsPlugin : SideEffectPlugin<ToastsSideEffectMediator, Nothing> {

    override val mediatorClass: Class<ToastsSideEffectMediator>
        get() = ToastsSideEffectMediator::class.java

    private var current: SideEffectMediator<Nothing>? = null

    override fun createMediator(applicationContext: Context): SideEffectMediator<Nothing> {
        if(current == null)
            current = ToastsSideEffectMediator(applicationContext)
        return current!!
    }
}