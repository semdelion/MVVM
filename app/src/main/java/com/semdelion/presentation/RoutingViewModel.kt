package com.semdelion.presentation

import com.semdelion.domain.repositories.accounts.IAccountsRepository
import com.semdelion.presentation.core.utils.LiveEvent
import com.semdelion.presentation.core.utils.MutableLiveEvent
import com.semdelion.presentation.core.utils.publishEvent
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import kotlinx.coroutines.launch

class RoutingViewModel(
    private val accountsRepository: IAccountsRepository
) : BaseViewModel() {

    private val _launchMainScreenEvent = MutableLiveEvent<Boolean>()
    val launchMainScreenEvent: LiveEvent<Boolean> = _launchMainScreenEvent

    init {
        viewModelScope.launch {
            _launchMainScreenEvent.publishEvent(accountsRepository.isSignedIn())
        }
    }
}