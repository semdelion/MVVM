package com.semdelion.presentaion.core

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.semdelion.presentaion.App
import com.semdelion.presentaion.MainActivity
import com.semdelion.presentaion.R
import com.semdelion.presentaion.core.navigator.IntermediateNavigator
import com.semdelion.presentaion.core.navigator.Navigator
import com.semdelion.presentaion.core.uiactions.UiActions
import com.semdelion.presentaion.core.utils.Event
import com.semdelion.presentaion.core.utils.ResourceActions
import com.semdelion.presentaion.core.views.utils.BaseScreen

const val ARG_SCREEN = "ARG_SCREEN"

class BaseActivityViewModel(
    val uiActions: UiActions,
    val navigator: IntermediateNavigator
) : ViewModel(), Navigator by navigator, UiActions by uiActions {

    override fun onCleared() {
        super.onCleared()
        navigator.clear()
    }
}