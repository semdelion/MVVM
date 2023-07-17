package com.semdelion.presentaion.core.navigator

import com.semdelion.presentaion.core.views.utils.BaseScreen

/**
 * Navigation for your application
 */
interface Navigator {
    /**
     * Launch a new screen at the top of back stack
     */
    fun launch(screen: BaseScreen)

    /**
     * Go back ti the previous screen and optionally send same results.
     */
    fun goBack(result: Any? = null)
}