package com.semdelion.presentation

import androidx.lifecycle.MutableLiveData
import com.semdelion.domain.repositories.accounts.IAccountsRepository
import com.semdelion.presentation.core.utils.toLiveData
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val accountsRepository: IAccountsRepository
) : BaseViewModel() {

    private val _username = MutableLiveData<String>()
    val username = _username.toLiveData()

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                _username.value =  if (it == null) "" else "@${it.username}"
            }
        }
    }
}