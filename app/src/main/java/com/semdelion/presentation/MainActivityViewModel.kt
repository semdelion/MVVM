package com.semdelion.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semdelion.domain.repositories.IAccountsRepository
import com.semdelion.presentation.core.utils.MutableLiveEvent
import com.semdelion.presentation.core.utils.publishEvent
import com.semdelion.presentation.core.utils.share
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val accountsRepository: IAccountsRepository
) : BaseViewModel() {

    private val _username = MutableLiveData<String>()
    val username = _username.share()

   /* private val _mainScreenLoading = MutableStateFlow(true)
    val mainScreenLoading = _mainScreenLoading.asStateFlow()

    private val _isSignedIn = MutableStateFlow<Boolean?>(null)
    val isSignedIn = _isSignedIn.asStateFlow()*/

    init {

        viewModelScope.launch {
            // listening for the current account and send the username to be displayed in the toolbar
            accountsRepository.getAccount().collect {
                if (it == null) {
                    _username.value = ""
                } else {
                    _username.value = "@${it.username}"
                }
            }
        }
    }
}