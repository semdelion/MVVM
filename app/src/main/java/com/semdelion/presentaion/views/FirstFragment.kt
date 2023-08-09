package com.semdelion.presentaion.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.semdelion.presentaion.R
import com.semdelion.presentaion.viewmodels.FirstViewModel
import com.semdelion.presentaion.core.views.utils.BaseScreen
import com.semdelion.presentaion.core.views.utils.HasScreenTitle
import com.semdelion.presentaion.core.views.factories.screenViewModel
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.semdelion.presentaion.core.views.BaseFragment
import com.semdelion.presentaion.databinding.FragmentFirstBinding
import kotlinx.coroutines.launch

class FirstFragment : BaseFragment(), HasScreenTitle {
    class Screen : BaseScreen

    override val viewModel by screenViewModel<FirstViewModel>()
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
                viewModel.resultLive.collect {
                    result -> binding.secondResultText.text = result
                }
            }
        }

        return binding.root
    }

    override fun getScreenTitle(): String {
        return "FirstFragment"
    }
}