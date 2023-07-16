package com.semdelion.mvvm.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.semdelion.mvvm.R
import com.semdelion.mvvm.presentation.viewmodels.SecondViewModel
import com.semdelion.mvvm.presentation.views.base.BaseFragment
import com.semdelion.mvvm.presentation.views.base.BaseScreen
import com.semdelion.mvvm.presentation.views.factories.screenViewModel

class SecondFragment : BaseFragment() {
    class Screen(
        val message: String
    ) : BaseScreen

    override val viewModel by screenViewModel<SecondViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        val messageText = view.findViewById<TextView>(R.id.message_text)

        viewModel.messageLive.observe(viewLifecycleOwner) {
            messageText.text = it
        }

        val button = view.findViewById<TextView>(R.id.send_back_button)

        button.setOnClickListener { viewModel.onBackPressed() }

        return view
    }
}