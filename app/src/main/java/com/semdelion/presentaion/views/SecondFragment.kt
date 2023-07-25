package com.semdelion.presentaion.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.semdelion.presentaion.R
import com.semdelion.presentaion.core.views.BaseFragment
import com.semdelion.presentaion.databinding.FragmentSecondBinding
import com.semdelion.presentaion.viewmodels.SecondViewModel
import com.semdelion.presentaion.core.views.utils.BaseScreen
import com.semdelion.presentaion.core.views.utils.HasScreenTitle
import com.semdelion.presentaion.core.views.factories.screenViewModel

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

        binding.sendBackButton.setOnClickListener { viewModel.onBack() }

        return binding.root
    }

    override fun getScreenTitle(): String {
        return "SecondFragment"
    }
}