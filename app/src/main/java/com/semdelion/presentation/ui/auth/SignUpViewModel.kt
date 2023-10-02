package com.semdelion.presentation.ui.auth

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.repositories.accounts.AccountAlreadyExistsException
import com.semdelion.domain.repositories.accounts.EmptyFieldException
import com.semdelion.domain.repositories.accounts.Field
import com.semdelion.domain.repositories.accounts.PasswordMismatchException
import com.semdelion.domain.repositories.accounts.models.SignUpData
import com.semdelion.domain.repositories.accounts.IAccountsRepository
import com.semdelion.presentation.R
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.resources.Resources
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.utils.requireValue
import com.semdelion.presentation.core.utils.share
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val accountsRepository: IAccountsRepository,
    private val navigationService: Navigator,
    private val toasts: Toasts,
    private val resources: Resources,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {


    private val _state = MutableLiveData(State())
    val state = _state.share()

    fun signUp(signUpData: SignUpData) {
        viewModelScope.launch {
            showProgress()
            try {
                accountsRepository.signUp(signUpData)
                toasts.toast(resources.getString(R.string.sign_up_success))
                goBack()
            } catch (e: EmptyFieldException) {
                processEmptyFieldException(e)
            } catch (e: PasswordMismatchException) {
                processPasswordMismatchException()
            } catch (e: AccountAlreadyExistsException) {
                processAccountAlreadyExistsException()
            } finally {
                hideProgress()
            }
        }
    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = when (e.field) {
            Field.Email -> _state.requireValue()
                .copy(emailErrorMessageRes = R.string.field_is_empty)
            Field.Username -> _state.requireValue()
                .copy(usernameErrorMessageRes = R.string.field_is_empty)
            Field.Password -> _state.requireValue()
                .copy(passwordErrorMessageRes = R.string.field_is_empty)
            else -> throw IllegalStateException("Unknown field")
        }
    }

    private fun processPasswordMismatchException() {
        _state.value = _state.requireValue()
            .copy(repeatPasswordErrorMessageRes = R.string.password_mismatch)
    }

    private fun processAccountAlreadyExistsException() {
        _state.value = _state.requireValue()
            .copy(emailErrorMessageRes = R.string.account_already_exists)
    }

    private fun showProgress() {
        _state.value = State(signUpInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(signUpInProgress = false)
    }

    private fun goBack() = navigationService.goBack()

    data class State(
        @StringRes val emailErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val passwordErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val repeatPasswordErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val usernameErrorMessageRes: Int = NO_ERROR_MESSAGE,
        val signUpInProgress: Boolean = false,
    ) {
        val showProgress: Boolean get() = signUpInProgress
        val enableViews: Boolean get() = !signUpInProgress
    }

    companion object {
        const val NO_ERROR_MESSAGE = 0
    }

}