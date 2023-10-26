package com.semdelion.presentation.ui.tabs.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.databinding.FragmentBoxBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoxFragment : BaseFragment() {

    override val viewModel by viewModels<BoxViewModel>()
    private lateinit var binding: FragmentBoxBinding

    private val args: BoxFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_box, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.root.setBackgroundColor(args.color)

        binding.backButton.setOnClickListener {
            viewModel.goBack()
        }

        binding.openSecretButton.setOnClickListener {
            viewModel.goSecretBox()
        }

        binding.generateNumberButton.setOnClickListener {
            viewModel.goBackWithRandom()
        }

        return binding.root
    }
}