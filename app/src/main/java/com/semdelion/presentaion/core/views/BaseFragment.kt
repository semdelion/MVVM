package com.semdelion.presentaion.core.views

import androidx.fragment.app.Fragment
import com.semdelion.presentaion.MainActivity
import com.semdelion.presentaion.core.viewmodels.BaseViewModel

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates() {
        (requireActivity() as MainActivity).notifyScreenUpdate()
    }
}