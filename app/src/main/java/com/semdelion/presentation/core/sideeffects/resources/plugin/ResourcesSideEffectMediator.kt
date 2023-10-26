package com.semdelion.presentation.core.sideeffects.resources.plugin

import android.content.Context
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.resources.Resources
import javax.inject.Inject

class ResourcesSideEffectMediator  @Inject constructor(
    private val appContext: Context
) : SideEffectMediator<Nothing>(), Resources {

    override fun getString(resId: Int, vararg args: Any): String {
        return appContext.getString(resId, *args)
    }
}