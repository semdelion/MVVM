package com.semdelion.presentation.ui.tabs.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.FragmentPushBinding

class PushFragment : BaseFragment() {

    private lateinit var binding: FragmentPushBinding

    override val viewModel by viewModel<PushViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_push, container,false)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        return binding.root
    }
}