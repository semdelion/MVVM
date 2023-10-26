package com.semdelion.presentation.core.sideeffects.permissions.plugin

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.semdelion.domain.core.tasks.models.ErrorResult
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.permissions.Permissions
import com.semdelion.presentation.core.utils.callback.Emitter
import com.semdelion.presentation.core.utils.toEmitter
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class PermissionsSideEffectMediator  @Inject constructor (
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