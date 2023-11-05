package com.semdelion.presentation.core.sideeffects.permissions.plugin

import android.content.Context
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.SideEffectPlugin
import com.semdelion.presentation.core.sideeffects.SideEffectProvider
import com.semdelion.presentation.core.sideeffects.permissions.Permissions
import dagger.hilt.android.EntryPointAccessors

/**
 * Plugin for managing permissions from view-models.
 * This plugin allows using [Permissions] interface to view-model constructor.
 */
class PermissionsPlugin :
    SideEffectPlugin<PermissionsSideEffectMediator, PermissionsSideEffectImpl> {

    override val mediatorClass: Class<PermissionsSideEffectMediator>
        get() = PermissionsSideEffectMediator::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<PermissionsSideEffectImpl> {
        val sideEffectProvider = EntryPointAccessors.fromApplication(applicationContext, SideEffectProvider::class.java)
        return sideEffectProvider.getPermissions() as SideEffectMediator<PermissionsSideEffectImpl>
    }

    override fun createImplementation(mediator: PermissionsSideEffectMediator): PermissionsSideEffectImpl {
        return PermissionsSideEffectImpl(mediator.retainedState)
    }
}