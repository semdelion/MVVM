package com.semdelion.mvvm.presentation.views.base

import androidx.fragment.app.Fragment
import com.semdelion.mvvm.presentation.viewmodels.base.BaseViewModel
import com.semdelion.mvvm.MainActivity

abstract class BaseFragment: Fragment() {

    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates() {
        (requireActivity() as MainActivity).notifyScreenUpdate()
    }

}