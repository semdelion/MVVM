package com.semdelion.presentation.ui.tabs.profile

import androidx.lifecycle.MutableLiveData
import androidx.navigation.navOptions
import com.semdelion.domain.repositories.accounts.models.Account
import com.semdelion.domain.repositories.accounts.IAccountsRepository
import com.semdelion.presentation.R
import com.semdelion.presentation.core.sideeffects.navigator.utils.NavCommandDirections
import com.semdelion.presentation.core.sideeffects.navigator.utils.NavCommandResId
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.utils.toLiveData

import com.semdelion.presentation.core.viewmodels.BaseViewModel
import com.semdelion.presentation.ui.tabs.TabsFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel  @Inject constructor(
    private val accountsRepository: IAccountsRepository,
    private val navigationService: Navigator
) : BaseViewModel() {

    private val _account = MutableLiveData<Account>()
    val account = _account.toLiveData()

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