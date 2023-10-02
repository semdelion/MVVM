package com.semdelion.presentation.ui.tabs.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.repositories.accounts.EmptyFieldException
import com.semdelion.domain.repositories.accounts.IAccountsRepository
import com.semdelion.presentation.R
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.resources.Resources
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.utils.MutableLiveEvent
import com.semdelion.presentation.core.utils.publishEvent
import com.semdelion.presentation.core.utils.share
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val accountsRepository: IAccountsRepository,
    private val navigationService: Navigator,
    private val toasts: Toasts,
    private val resources: Resources,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _initialUsernameEvent = MutableLiveEvent<String>()
    val initialUsernameEvent = _initialUsernameEvent.share()

    private val _saveInProgress = MutableLiveData(false)
    val saveInProgress = _saveInProgress.share()

    init {
        viewModelScope.launch {
            val account = accountsRepository.getAccount()
                .filterNotNull()
                .first()
            _initialUsernameEvent.publishEvent(account.username)
        }
    }

    fun saveUsername(newUsername: String) {
        viewModelScope.launch {
            showProgress()
            try {
                accountsRepository.updateAccountUsername(newUsername)
                navigationService.goBack()
            } catch (e: EmptyFieldException) {
                hideProgress()
                toasts.toast(resources.getString(R.string.field_is_empty))
            }
        }
    }

    fun goCancel() {
        navigationService.goBack()
    }

    private fun showProgress() {
        _saveInProgress.value = true
    }

    private fun hideProgress() {
        _saveInProgress.value = false
    }
}