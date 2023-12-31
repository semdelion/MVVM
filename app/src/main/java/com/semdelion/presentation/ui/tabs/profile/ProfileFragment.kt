package com.semdelion.presentation.ui.tabs.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.semdelion.domain.repositories.accounts.models.Account
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import java.text.SimpleDateFormat
import java.util.Date
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding

    override val viewModel by viewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.logoutButton.setOnClickListener { viewModel.logout() }

        observeAccountDetails()

        return binding.root
    }

    private fun observeAccountDetails() {
        val formatter = SimpleDateFormat.getDateTimeInstance()
        viewModel.account.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            binding.emailTextView.setText(it.email)
            binding.usernameTextView.setText(it.username)
            binding.createdAtTextView.setText(if (it.createdAt == Account.UNKNOWN_CREATED_AT)
                getString(R.string.placeholder)
            else
                formatter.format(Date(it.createdAt)))
            binding.usernameInputLayout.setEndIconOnClickListener { _ ->
                viewModel.toEditProfile()
            }
        }
    }
}