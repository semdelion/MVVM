package com.semdelion.mvvm.presentation.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.semdelion.mvvm.R
import com.semdelion.mvvm.presentation.viewmodels.SecondViewModel
import com.semdelion.mvvm.presentation.views.base.BaseFragment
import com.semdelion.mvvm.presentation.views.base.BaseScreen
import com.semdelion.mvvm.presentation.views.factories.screenViewModel

class SecondFragment : BaseFragment() {

    class Screen(
        val info: String
    ): BaseScreen

    override val viewModel by screenViewModel<SecondViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

}