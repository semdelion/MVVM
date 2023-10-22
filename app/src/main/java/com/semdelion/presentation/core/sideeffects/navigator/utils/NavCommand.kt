package com.semdelion.presentation.core.sideeffects.navigator.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

interface INavCommand {
    var navOptions: NavOptions?

    fun launch(navController: NavController)
}

class NavCommandDirections(
    val direction: NavDirections,
    override var navOptions: NavOptions? = null
) : INavCommand {

    override fun launch(navController: NavController) {
        navController.navigate(direction, navOptions)
    }
}

class NavCommandResId(
    @IdRes val resId: Int,
    val args: Bundle? = null,
    override var navOptions: NavOptions? = null
) : INavCommand {

    override fun launch(navController: NavController) {
        navController.navigate(resId, args, navOptions)
    }
}