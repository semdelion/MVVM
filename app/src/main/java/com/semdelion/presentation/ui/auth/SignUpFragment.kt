package com.semdelion.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.semdelion.domain.repositories.accounts.models.SignUpData

import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {

    private lateinit var binding: FragmentSignUpBinding

    override val viewModel by viewModels<SignUpViewModel>()

    private val args by navArgs<SignUpFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.createAccountButton.setOnClickListener { onCreateAccountButtonPressed() }

        if (savedInstanceState == null && getEmailArgument() != null) {
            binding.emailEditText.setText(getEmailArgument())
        }

        observeState()

        return binding.root
    }

    private fun onCreateAccountButtonPressed() {
        val signUpData = SignUpData(
            email = binding.emailEditText.text.toString(),
            username = binding.usernameEditText.text.toString(),
            password = binding.passwordEditText.text.toString(),
            repeatPassword = binding.repeatPasswordEditText.text.toString(),
        )
        viewModel.signUp(signUpData)
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) { state: SignUpViewModel.State ->
        binding.createAccountButton.isEnabled = state.enableViews
        binding.emailTextInput.isEnabled = state.enableViews
        binding.usernameTextInput.isEnabled = state.enableViews
        binding.passwordTextInput.isEnabled = state.enableViews
        binding.repeatPasswordTextInput.isEnabled = state.enableViews

        fillError(binding.emailTextInput, state.emailErrorMessageRes)
        fillError(binding.usernameTextInput, state.usernameErrorMessageRes)
        fillError(binding.passwordTextInput, state.passwordErrorMessageRes)
        fillError(binding.repeatPasswordTextInput, state.repeatPasswordErrorMessageRes)

        binding.progressBar.visibility = if (state.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun fillError(input: TextInputLayout, @StringRes stringRes: Int) {
        if (stringRes == SignUpViewModel.NO_ERROR_MESSAGE) {
            input.error = null
            input.isErrorEnabled = false
        } else {
            input.error = getString(stringRes)
            input.isErrorEnabled = true
        }
    }

    private fun getEmailArgument(): String? = args.email

}