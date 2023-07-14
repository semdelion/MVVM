package com.semdelion.mvvm.presentation.navigations

interface UiActions {

    fun toast(message: String)

    fun getString(messageRes: Int, vararg args: Any): String
}