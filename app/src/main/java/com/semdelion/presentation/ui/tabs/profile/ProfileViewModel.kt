package com.semdelion.presentation.ui.tabs.profile

import androidx.lifecycle.MutableLiveData
import com.semdelion.domain.models.Account
import com.semdelion.domain.repositories.IAccountsRepository
import com.semdelion.presentation.core.utils.MutableLiveEvent
import com.semdelion.presentation.core.utils.publishEvent
import com.semdelion.presentation.core.utils.share

import com.semdelion.presentation.core.viewmodels.BaseViewModel
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val accountsRepository: IAccountsRepository
) : BaseViewModel() {

    private val _account = MutableLiveData<Account>()
    val account = _account.share()

    private val _restartFromLoginEvent = MutableLiveEvent<Unit>()
    val restartWithSignInEvent = _restartFromLoginEvent.share()

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                _account.value = it
            }
        }
    }

    fun logout() {
        // now logout is not async, so simply call it and restart the app from login screen
        accountsRepository.logout()
        restartAppFromLoginScreen()
    }

    private fun restartAppFromLoginScreen() {
        _restartFromLoginEvent.publishEvent()
    }

}