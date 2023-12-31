package com.semdelion.presentation.ui.tabs.dashboard

import android.graphics.Color
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.FragmentRootBinding

class RootFragment : BaseFragment() {

    private lateinit var binding: FragmentRootBinding
    override val viewModel by viewModel<RootViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_root, container,false)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.openGreenBoxButton.setOnClickListener {
            viewModel.openBox(Color.rgb(55,250,55), "Green")
        }
        binding.openYellowBoxButton.setOnClickListener {
            viewModel.openBox(Color.rgb(255,220,7), "Yellow")
        }

        binding.firstButton.setOnClickListener {
            viewModel.toFirstFragment()
        }

        binding.servicesButton.setOnClickListener {
            viewModel.toServicesFragment()
        }

        return binding.root
    }

}