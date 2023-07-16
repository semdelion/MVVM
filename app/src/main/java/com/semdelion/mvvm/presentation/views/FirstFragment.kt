package com.semdelion.mvvm.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.semdelion.mvvm.R
import com.semdelion.mvvm.presentation.viewmodels.FirstViewModel
import com.semdelion.mvvm.presentation.views.base.BaseFragment
import com.semdelion.mvvm.presentation.views.base.BaseScreen
import com.semdelion.mvvm.presentation.views.base.HasScreenTitle
import com.semdelion.mvvm.presentation.views.factories.screenViewModel
import androidx.databinding.DataBindingUtil
import com.semdelion.mvvm.databinding.FragmentFirstBinding

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

        viewModel.resultLive.observe(viewLifecycleOwner) {
            binding.secondResultText.text = it
        }

        return binding.root
    }

    override fun getScreenTitle(): String {
        return "FirstFragment"
    }
}