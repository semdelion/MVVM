package com.semdelion.presentation.ui.auth

import androidx.lifecycle.MutableLiveData
import com.semdelion.domain.repositories.accounts.AuthException
import com.semdelion.domain.repositories.accounts.EmptyFieldException
import com.semdelion.domain.repositories.accounts.Field
import com.semdelion.domain.repositories.accounts.IAccountsRepository
import com.semdelion.presentation.R
import com.semdelion.presentation.core.sideeffects.navigator.utils.NavCommandDirections
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.resources.Resources
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.utils.MutableUnitLiveEvent
import com.semdelion.presentation.core.utils.publishEvent
import com.semdelion.presentation.core.utils.requireValue
import com.semdelion.presentation.core.utils.toLiveData

import com.semdelion.presentation.core.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountsRepository: IAccountsRepository,
    private val navigationService: Navigator,
    private val resources: Resources,
    private val toasts: Toasts
) : BaseViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.toLiveData()

    private val _clearPasswordEvent = MutableUnitLiveEvent()
    val clearPasswordEvent = _clearPasswordEvent.toLiveData()

    fun signIn(email: String, password: String) = viewModelScope.launch {
        showProgress()
        try {
            accountsRepository.signIn(email, password)
            navigationService.launch(NavCommandDirections(SignInFragmentDirections.actionSignInFragmentToTabsFragment()))
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        } catch (e: AuthException) {
            processAuthException()
        }
    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.requireValue().copy(
            emptyEmailError = e.field == Field.Email,
            emptyPasswordError = e.field == Field.Password,
            signInInProgress = false
        )
    }

    private fun processAuthException() {
        _state.value = _state.requireValue().copy(
            signInInProgress = false
        )
        clearPasswordField()
        toasts.toast(resources.getString(R.string.invalid_email_or_password))
    }

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    private fun clearPasswordField() = _clearPasswordEvent.publishEvent()

    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
        val enableViews: Boolean get() = !signInInProgress
    }
}