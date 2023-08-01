package com.semdelion.presentaion.core.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semdelion.domain.models.ErrorResult
import com.semdelion.domain.models.Result
import com.semdelion.domain.models.SuccessResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
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
                liveResult.postValue(ErrorResult(ex))
            }
        }
    }

    private fun clearViewModelScope() {
        viewModelScope.cancel()
    }
}