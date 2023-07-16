package com.semdelion.mvvm.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.semdelion.mvvm.R
import com.semdelion.mvvm.databinding.FragmentFirstBinding
import com.semdelion.mvvm.databinding.FragmentSecondBinding
import com.semdelion.mvvm.presentation.viewmodels.SecondViewModel
import com.semdelion.mvvm.presentation.views.base.BaseFragment
import com.semdelion.mvvm.presentation.views.base.BaseScreen
import com.semdelion.mvvm.presentation.views.base.HasScreenTitle
import com.semdelion.mvvm.presentation.views.factories.screenViewModel

class SecondFragment : BaseFragment(), HasScreenTitle {
    class Screen(
        val message: String
    ) : BaseScreen

    override val viewModel by screenViewModel<SecondViewModel>()
    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.messageLive.observe(viewLifecycleOwner) {
            binding.messageText.text = it
        }

        binding.sendBackButton.setOnClickListener { viewModel.onBackPressed() }

        return binding.root
    }

    override fun getScreenTitle(): String {
        return "SecondFragment"
    }
}