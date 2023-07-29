package com.semdelion.presentaion.core.sideeffects.dialogs.plugin

import com.semdelion.domain.core.tasks.Task
import com.semdelion.domain.models.ErrorResult
import com.semdelion.presentaion.core.sideeffects.SideEffectMediator
import com.semdelion.presentaion.core.sideeffects.dialogs.Dialogs
import com.semdelion.presentaion.core.tasks.callback.CallbackTask
import com.semdelion.presentaion.core.tasks.callback.Emitter

class DialogsSideEffectMediator : SideEffectMediator<DialogsSideEffectImpl>(), Dialogs {

    var retainedState = RetainedState()

    override fun show(dialogConfig: DialogConfig): Task<Boolean> = CallbackTask.create { emitter ->
        if (retainedState.record != null) {
            // for now allowing only 1 active dialog at a time
            emitter.emit(ErrorResult(IllegalStateException("Can't launch more than 1 dialog at a time")))
            return@create
        }

        val wrappedEmitter = Emitter.wrap(emitter) {
            retainedState.record = null
        }

        val record = DialogRecord(wrappedEmitter, dialogConfig)
        wrappedEmitter.setCancelListener {
            target { implementation ->
                implementation.removeDialog()
            }
        }

        target { implementation ->
            implementation.showDialog(record)
        }

        retainedState.record = record
    }

    class DialogRecord(
        val emitter: Emitter<Boolean>,
        val config: DialogConfig
    )

    class RetainedState(
        var record: DialogRecord? = null
    )
}