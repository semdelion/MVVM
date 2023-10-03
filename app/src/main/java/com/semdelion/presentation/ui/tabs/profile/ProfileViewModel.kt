package com.semdelion.presentation.ui.tabs.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.navOptions
import com.semdelion.domain.repositories.accounts.models.Account
import com.semdelion.domain.repositories.accounts.IAccountsRepository
import com.semdelion.presentation.R
import com.semdelion.presentation.core.sideeffects.navigator.NavCommandDirections
import com.semdelion.presentation.core.sideeffects.navigator.NavCommandResId
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.utils.MutableLiveEvent
import com.semdelion.presentation.core.utils.publishEvent
import com.semdelion.presentation.core.utils.share

import com.semdelion.presentation.core.viewmodels.BaseViewModel
import com.semdelion.presentation.ui.tabs.TabsFragmentDirections
import com.semdelion.presentation.ui.utils.findTopNavController
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val accountsRepository: IAccountsRepository,
    private val navigationService: Navigator
) : BaseViewModel() {

    private val _account = MutableLiveData<Account>()
    val account = _account.share()



    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                _account.value = it
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            accountsRepository.logout()
            navigationService.launch(NavCommandResId(R.id.signInFragment, null, navOptions {
                popUpTo(R.id.tabsFragment) {inclusive = true}
            }))
        }
    }

    fun toEditProfile() {
        navigationService.launch(NavCommandDirections(TabsFragmentDirections.actionTabsFragmentToEditProfileFragment()))
    }
}