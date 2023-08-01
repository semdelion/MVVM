package com.semdelion.presentaion.core.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semdelion.domain.core.tasks.dispatchers.Dispatcher
import com.semdelion.domain.models.ErrorResult
import com.semdelion.domain.models.LoadingResult
import com.semdelion.domain.models.Result
import com.semdelion.domain.models.SuccessResult
import kotlinx.coroutines.launch
import java.lang.Exception

open class BaseViewModel: ViewModel() {

    open fun onResult(result: Any) {}

    open fun onBackPressed(): Boolean {
        clearTasks()
        return false
    }

    override fun onCleared() {
        super.onCleared()
        clearTasks()
    }

    fun <T> into(liveResult: MutableLiveData<Result<T>>, block: suspend () -> T) {
        viewModelScope.launch {
            try {
                liveResult.postValue(SuccessResult(block()))
            } catch (ex: Exception) {
                liveResult.postValue(ErrorResult(ex))
            }
        }
    }

    private fun clearTasks() {
    }
}