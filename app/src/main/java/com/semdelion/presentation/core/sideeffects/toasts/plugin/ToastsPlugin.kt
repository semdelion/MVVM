package com.semdelion.presentation.core.sideeffects.toasts.plugin

import android.content.Context
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.SideEffectPlugin
import com.semdelion.presentation.core.sideeffects.SideEffectProvider
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import dagger.hilt.android.EntryPointAccessors

/**
 * Plugin for displaying toast messages from view-models.
 * Allows adding [Toasts] interface to the view-model constructor.
 */
class ToastsPlugin : SideEffectPlugin<ToastsSideEffectMediator, Nothing> {

    override val mediatorClass: Class<ToastsSideEffectMediator>
        get() = ToastsSideEffectMediator::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<Nothing> {
        val sideEffectProvider = EntryPointAccessors.fromApplication(applicationContext, SideEffectProvider::class.java)

        return sideEffectProvider.getToasts() as SideEffectMediator<Nothing>
    }
}