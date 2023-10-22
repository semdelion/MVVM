package com.semdelion.presentation.ui.tabs.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.FragmentSecretBinding

class SecretFragment : BaseFragment() {

    override val viewModel by viewModel<SecretViewModel>()
    private lateinit var binding: FragmentSecretBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_secret, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.backButton.setOnClickListener {
            viewModel.goBack()
        }

        binding.closeBoxButton.setOnClickListener {
            //TODO navigation
            findNavController().popBackStack(R.id.rootFragment, false)
        }

        return binding.root
    }
}