package com.semdelion.presentation.ui.tabs.dashboard.tmp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.databinding.FragmentSecondBinding
import com.semdelion.presentation.core.views.utils.HasScreenTitle
import com.semdelion.presentation.core.views.factories.viewModel
import kotlinx.coroutines.launch

class SecondFragment : BaseFragment(), HasScreenTitle {

    override val viewModel by viewModel<SecondViewModel>()

    private lateinit var binding: FragmentSecondBinding

    private val args: SecondFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messageLive.collect { result: String ->
                    binding.messageText.text = result
                }
            }
        }

        binding.sendBackButton.setOnClickListener {
            viewModel.onBack()
        }

        return binding.root
    }

    override fun getScreenTitle(): String {
        return "SecondFragment"
    }
}