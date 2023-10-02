package com.semdelion.presentation.core.views

import androidx.fragment.app.Fragment
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import com.semdelion.presentation.core.views.utils.FragmentsHolder

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates() {
        (requireActivity() as FragmentsHolder).notifyScreenUpdate()
    }
}