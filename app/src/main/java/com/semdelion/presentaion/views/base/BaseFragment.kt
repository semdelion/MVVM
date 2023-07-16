package com.semdelion.presentaion.views.base

import androidx.fragment.app.Fragment
import com.semdelion.presentaion.MainActivity
import com.semdelion.presentaion.viewmodels.base.BaseViewModel

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates() {
        (requireActivity() as MainActivity).notifyScreenUpdate()
    }
}