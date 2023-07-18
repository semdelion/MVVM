package com.semdelion.presentaion.core.views.utils

import com.semdelion.presentaion.core.BaseActivityViewModel

interface FragmentsHolder {
    fun notifyScreenUpdate()

    fun getBaseActivityViewModel() : BaseActivityViewModel
}