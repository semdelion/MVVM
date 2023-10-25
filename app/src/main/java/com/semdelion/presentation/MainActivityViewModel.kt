package com.semdelion.presentation

import androidx.lifecycle.MutableLiveData
import com.semdelion.domain.repositories.accounts.IAccountsRepository
import com.semdelion.presentation.core.utils.toLiveData
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
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