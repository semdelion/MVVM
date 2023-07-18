package com.semdelion.presentaion.core.views

import androidx.fragment.app.Fragment
import com.semdelion.presentaion.core.viewmodels.BaseViewModel
import com.semdelion.presentaion.core.views.utils.FragmentsHolder

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates() {
        (requireActivity() as FragmentsHolder).notifyScreenUpdate()
    }
}