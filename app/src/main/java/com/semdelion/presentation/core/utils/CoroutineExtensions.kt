package com.semdelion.presentation.core.utils

import com.semdelion.domain.core.tasks.models.ErrorResult
import com.semdelion.domain.core.tasks.models.FinalResult
import com.semdelion.domain.core.tasks.models.SuccessResult
import com.semdelion.presentation.core.utils.callback.CancelListener
import com.semdelion.presentation.core.utils.callback.Emitter
import kotlinx.coroutines.CancellableContinuation
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.resume


fun <T> CancellableContinuation<T>.toEmitter(): Emitter<T> {
    return object : Emitter<T> {

        var isDone = AtomicBoolean(false)

        override fun emit(finalResult: FinalResult<T>) {
            if (isDone.compareAndSet(false, true)) {
                when (finalResult) {
                    is SuccessResult -> resume(finalResult.data)
                    is ErrorResult -> resumeWithException(finalResult.exception)
                }
            }
        }

        override fun setCancelListener(cancelListener: CancelListener) {
            invokeOnCancellation { cancelListener() }
        }
    }
}