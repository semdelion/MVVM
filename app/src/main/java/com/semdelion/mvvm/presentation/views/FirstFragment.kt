package com.semdelion.mvvm.presentation.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.semdelion.mvvm.R
import com.semdelion.mvvm.presentation.viewmodels.FirstViewModel
import com.semdelion.mvvm.presentation.viewmodels.SecondViewModel
import com.semdelion.mvvm.presentation.views.base.BaseFragment
import com.semdelion.mvvm.presentation.views.base.BaseScreen
import com.semdelion.mvvm.presentation.views.factories.screenViewModel

class FirstFragment : BaseFragment() {

    class Screen: BaseScreen

    override val viewModel by screenViewModel<FirstViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        val button = view.findViewById<Button>(R.id.sendTextButton)

        button.setOnClickListener { viewModel.sendText() }

        return view
    }
}