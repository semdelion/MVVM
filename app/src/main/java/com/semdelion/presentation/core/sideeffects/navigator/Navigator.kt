package com.semdelion.presentation.core.sideeffects.navigator

import androidx.navigation.NavDirections

interface Navigator {

    /**
     * Launch a new view at the top of back stack.
     */

    fun launch(direction: NavDirections)

    /**
     * Go back to the previous screen and optionally send some results.
     */
    fun goBack(result: Any? = null)
}