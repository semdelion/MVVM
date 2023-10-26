package com.semdelion.presentation.core.sideeffects.permissions.plugin

import android.content.Context
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.SideEffectPlugin
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogsSideEffectMediator

/**
 * Plugin for managing permissions from view-models.
 * This plugin allows using [Permissions] interface to view-model constructor.
 */
class PermissionsPlugin :
    SideEffectPlugin<PermissionsSideEffectMediator, PermissionsSideEffectImpl> {

    override val mediatorClass: Class<PermissionsSideEffectMediator>
        get() = PermissionsSideEffectMediator::class.java

    private var current: SideEffectMediator<PermissionsSideEffectImpl>? = null

    override fun createMediator(applicationContext: Context): SideEffectMediator<PermissionsSideEffectImpl> {
        if(current == null)
            current = PermissionsSideEffectMediator(applicationContext)
        return current!!
    }

    override fun createImplementation(mediator: PermissionsSideEffectMediator): PermissionsSideEffectImpl {
        return PermissionsSideEffectImpl(mediator.retainedState)
    }
}