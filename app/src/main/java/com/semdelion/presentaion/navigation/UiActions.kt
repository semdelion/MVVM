package com.semdelion.presentaion.navigation

interface UiActions {

    fun toast(message: String)

    fun getString(messageRes: Int, vararg args: Any): String
}