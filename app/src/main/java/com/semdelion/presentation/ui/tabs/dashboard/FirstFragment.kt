package com.semdelion.presentation.ui.tabs.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.utils.HasScreenTitle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.semdelion.presentation.databinding.FragmentFirstBinding
import com.semdelion.presentation.core.views.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstFragment : BaseFragment(), HasScreenTitle {

    override val viewModel by viewModels<FirstViewModel>()
    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.sendTextButton.setOnClickListener { viewModel.sendText() }

        binding.requestPermission.setOnClickListener { viewModel.requestPermission() }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.resultFlow.collectLatest {
                    result: String -> binding.secondResultText.text = result
                }
            }
        }

        return binding.root
    }

    override fun getScreenTitle(): String {
        return "FirstFragment"
    }
}