package com.semdelion.presentaion.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.semdelion.presentaion.R
import com.semdelion.presentaion.core.views.BaseFragment
import com.semdelion.presentaion.core.views.factories.viewModel
import com.semdelion.presentaion.databinding.FragmentThirdBinding
import com.semdelion.presentaion.viewmodels.ThirdViewModel

class ThirdFragment : BaseFragment()
{
    override val viewModel by viewModel<ThirdViewModel>()

    private lateinit var binding: FragmentThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
}