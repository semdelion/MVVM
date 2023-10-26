package com.semdelion.presentation.core.sideeffects.dialogs.plugin

import android.content.Context
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.SideEffectPlugin

class DialogsPlugin : SideEffectPlugin<DialogsSideEffectMediator, DialogsSideEffectImpl> {

    override val mediatorClass: Class<DialogsSideEffectMediator>
        get() = DialogsSideEffectMediator::class.java

    private var current: SideEffectMediator<DialogsSideEffectImpl>? = null

    override fun createMediator(applicationContext: Context): SideEffectMediator<DialogsSideEffectImpl> {
        if(current == null)
            current = DialogsSideEffectMediator()
        return current!!
    }

    override fun createImplementation(mediator: DialogsSideEffectMediator): DialogsSideEffectImpl {
        return DialogsSideEffectImpl(mediator.retainedState)
    }
}