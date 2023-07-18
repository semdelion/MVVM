package com.semdelion.presentaion.core.viewmodels

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    open fun onResult(result: Any) { }
}