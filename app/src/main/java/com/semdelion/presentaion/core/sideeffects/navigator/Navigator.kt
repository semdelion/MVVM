package com.semdelion.presentaion.core.sideeffects.navigator

import com.semdelion.presentaion.core.views.utils.BaseScreen

interface Navigator {

    /**
     * Launch a new screen at the top of back stack.
     */
    fun launch(screen: BaseScreen)

    /**
     * Go back to the previous screen and optionally send some results.
     */
    fun goBack(result: Any? = null)

}