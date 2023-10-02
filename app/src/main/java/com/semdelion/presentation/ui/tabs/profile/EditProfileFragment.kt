package com.semdelion.presentation.ui.tabs.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.semdelion.presentation.R
import com.semdelion.presentation.core.utils.observeEvent
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.FragmentEditProfileBinding

class EditProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentEditProfileBinding

    override val viewModel by viewModel<EditProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.saveButton.setOnClickListener { onSaveButtonPressed() }
        binding.cancelButton.setOnClickListener { viewModel.goCancel() }
        if (savedInstanceState == null) listenInitialUsernameEvent()
        observeSaveInProgress()

        return binding.root
    }

    private fun onSaveButtonPressed() {
        viewModel.saveUsername(binding.usernameEditText.text.toString())
    }

    private fun observeSaveInProgress() = viewModel.saveInProgress.observe(viewLifecycleOwner) { it: Boolean ->
        if (it) {
            binding.progressBar.visibility = View.VISIBLE
            binding.saveButton.isEnabled = false
            binding.usernameTextInput.isEnabled = false
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.saveButton.isEnabled = true
            binding.usernameTextInput.isEnabled = true
        }
    }

    private fun listenInitialUsernameEvent() = viewModel.initialUsernameEvent.observeEvent(viewLifecycleOwner) { username: String ->
        binding.usernameEditText.setText(username)
    }
}