package com.semdelion.presentaion.core.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semdelion.domain.core.Task
import com.semdelion.domain.core.TaskListener
import com.semdelion.domain.models.LoadingResult
import com.semdelion.domain.models.Result

open class BaseViewModel : ViewModel() {

    private val tasks = mutableSetOf<Task<*>>()

    open fun onResult(result: Any) {}

    override fun onCleared() {
        super.onCleared()
        tasks.forEach { it.cancel() }
        tasks.clear()
    }

    fun <T> Task<T>.safeEnqueue(listener: TaskListener<T>? = null) {
        tasks.add(this)
        this.enqueue {
            tasks.remove(this)
            listener?.invoke(it)
        }
    }

    fun <T> Task<T>.into(liveResult: MutableLiveData<Result<T>>) {
        liveResult.value = LoadingResult()
        this.safeEnqueue {
            liveResult.value = it
        }
    }
}