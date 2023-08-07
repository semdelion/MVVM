package com.semdelion.presentaion.core.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.semdelion.domain.models.ErrorResult
import com.semdelion.domain.models.Result
import com.semdelion.domain.models.SuccessResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

open class BaseViewModel: ViewModel() {

    private val coroutineContext = SupervisorJob() + Dispatchers.Main.immediate
    protected val viewModelScope: CoroutineScope = CoroutineScope(coroutineContext)

    open fun onResult(result: Any) {}

    open fun onBackPressed(): Boolean {
        clearViewModelScope()
        return false
    }

    override fun onCleared() {
        super.onCleared()
        clearViewModelScope()
    }

    fun <T> into(liveResult: MutableLiveData<Result<T>>, block: suspend () -> T) {
        viewModelScope.launch {
            try {
                liveResult.postValue(SuccessResult(block()))
            } catch (ex: Exception) {
                if (ex !is CancellationException)
                    liveResult.postValue(ErrorResult(ex))
            }
        }
    }

    fun <T> into(stateFlow: MutableStateFlow<Result<T>>, block: suspend () -> T) {
        viewModelScope.launch {
            try {
                stateFlow.value = SuccessResult(block())
            } catch (ex: Exception) {
                if (ex !is CancellationException)
                    stateFlow.value = ErrorResult(ex)
            }
        }
    }

    fun <T> SavedStateHandle.getStateFlow(key: String, initialValue: T): MutableStateFlow<T> {
        val savedStateHandle = this
        val mutableFlow = MutableStateFlow(savedStateHandle[key] ?: initialValue)
        viewModelScope.launch {
            mutableFlow.collect() {
                savedStateHandle[key] = it
            }
        }

        viewModelScope.launch {
            savedStateHandle.getLiveData<T>(key).asFlow().collect {
                mutableFlow.value = it
            }
        }

        return mutableFlow
    }


    private fun clearViewModelScope() {
        viewModelScope.cancel()
    }
}