package com.semdelion.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.semdelion.presentation.R
import com.semdelion.presentation.core.utils.observeEvent
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment() {

    private lateinit var binding: FragmentSignInBinding
    override val viewModel by viewModel<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.signInButton.setOnClickListener { onSignInButtonPressed() }
        binding.signUpButton.setOnClickListener { onSignUpButtonPressed() }
        observeState()
        observeClearPasswordEvent()

        return binding.root
    }

    private fun onSignInButtonPressed() {
        viewModel.signIn(
            email = binding.emailEditText.text.toString(),
            password = binding.passwordEditText.text.toString()
        )
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) { result: SignInViewModel.State ->
        binding.emailTextInput.error = if (result.emptyEmailError) getString(R.string.field_is_empty) else null
        binding.passwordTextInput.error = if (result.emptyPasswordError) getString(R.string.field_is_empty) else null

        binding.emailTextInput.isEnabled = result.enableViews
        binding.passwordTextInput.isEnabled = result.enableViews
        binding.signInButton.isEnabled = result.enableViews
        binding.signUpButton.isEnabled = result.enableViews
        binding.progressBar.visibility = if (result.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeClearPasswordEvent() = viewModel.clearPasswordEvent.observeEvent(viewLifecycleOwner) {
        binding.passwordEditText.text?.clear()
    }

    private fun onSignUpButtonPressed() {
        val email = binding.emailEditText.text.toString()
        val emailArg = email.ifBlank { null }
        val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment(emailArg)
        findNavController().navigate(direction)
    }
}