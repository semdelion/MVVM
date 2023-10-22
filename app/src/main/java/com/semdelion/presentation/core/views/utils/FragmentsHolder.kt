package com.semdelion.presentation.core.views.utils

import com.semdelion.presentation.core.viewmodels.BaseActivityViewModel

interface FragmentsHolder {
    fun notifyScreenUpdate()

    fun getBaseActivityViewModel() : BaseActivityViewModel
}