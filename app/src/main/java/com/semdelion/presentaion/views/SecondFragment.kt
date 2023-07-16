package com.semdelion.presentaion.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.semdelion.presentaion.R
import com.semdelion.presentaion.databinding.FragmentSecondBinding
import com.semdelion.presentaion.viewmodels.SecondViewModel
import com.semdelion.presentaion.views.base.BaseFragment
import com.semdelion.presentaion.views.base.BaseScreen
import com.semdelion.presentaion.views.base.HasScreenTitle
import com.semdelion.presentaion.views.base.factories.screenViewModel

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