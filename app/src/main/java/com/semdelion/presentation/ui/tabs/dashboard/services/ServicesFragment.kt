package com.semdelion.presentation.ui.tabs.dashboard.services

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.databinding.FragmentServicesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServicesFragment : BaseFragment() {
    private lateinit var binding: FragmentServicesBinding
    override val viewModel by viewModels<ServicesViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container,false)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.backgroundButton.setOnClickListener {
            Intent(this.context, BackgroundServices::class.java).also {
                activity?.startService(it)
            }
        }

        binding.foregroundButton.setOnClickListener {
            Intent(this.context, ForegroundService::class.java).also {
                activity?.startForegroundService(it)
            }
        }
        return binding.root
    }
}