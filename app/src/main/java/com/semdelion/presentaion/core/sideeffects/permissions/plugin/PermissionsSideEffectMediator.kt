package com.semdelion.presentaion.core.sideeffects.permissions.plugin

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.semdelion.domain.models.ErrorResult
import com.semdelion.presentaion.core.sideeffects.SideEffectMediator
import com.semdelion.presentaion.core.sideeffects.permissions.Permissions
import com.semdelion.presentaion.core.utils.callback.Emitter
import com.semdelion.presentaion.core.utils.toEmitter
import kotlinx.coroutines.suspendCancellableCoroutine

class PermissionsSideEffectMediator(
    private val appContext: Context
) : SideEffectMediator<PermissionsSideEffectImpl>(), Permissions {

    val retainedState = RetainedState()

    override fun hasPermissions(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(appContext, permission) == PackageManager.PERMISSION_GRANTED
    }

    override suspend fun requestPermission(permission: String): PermissionStatus = suspendCancellableCoroutine { continuation ->
        val emitter = continuation.toEmitter()
        if (retainedState.emitter != null) {
            emitter.emit(ErrorResult(IllegalStateException("Only one permission request can be active")))
            return@suspendCancellableCoroutine
        }
        retainedState.emitter = emitter
        target { implementation ->
            implementation.requestPermission(permission)
        }
    }

    class RetainedState(
        var emitter: Emitter<PermissionStatus>? = null
    )
}