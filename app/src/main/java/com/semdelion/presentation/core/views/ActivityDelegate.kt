package com.semdelion.presentation.core.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.semdelion.presentation.core.sideeffects.SideEffectImplementationsHolder
import com.semdelion.presentation.core.sideeffects.SideEffectPlugin
import com.semdelion.presentation.core.sideeffects.SideEffectPluginsManager
import com.semdelion.presentation.core.utils.viewModelCreator
import com.semdelion.presentation.core.viewmodels.BaseActivityViewModel
import java.lang.Exception

/**
 * Delegate that manages side-effect plugins and contains common logic.
 * The following methods should be called from activity:
 * - [onBackPressed]
 * - [onSupportNavigateUp]
 * - [onCreate]
 * - [onSavedInstanceState]
 * - [onActivityResult]
 * - [onRequestPermissionsResult]
 */
class ActivityDelegate(
    private val activity: AppCompatActivity
) : DefaultLifecycleObserver {

    internal val sideEffectPluginsManager = SideEffectPluginsManager()

    private val activityViewModel by activity.viewModelCreator<BaseActivityViewModel> { BaseActivityViewModel() }

    private val implementersHolder = SideEffectImplementationsHolder()

    init {
        activity.lifecycle.addObserver(this)
    }

    /**
     * Call this method from [AppCompatActivity.onBackPressed].
     * Example:
     * ```
     * override fun onBackPressed() {
     *     if (!delegate.onBackPressed()) super.onBackPressed()
     * }
     * ```
     */
    fun onBackPressed(): Boolean {
        return implementersHolder.implementations.any { it.onBackPressed() }
    }

    /**
     * Call this method from [AppCompatActivity.onSupportNavigateUp]
     * If this method returns `null` you should call `super.onSupportNavigateUp()` if your activity.
     * Example:
     * ```
     * override fun onSupportNavigateUp(): Boolean {
     *    return delegate.onSupportNavigateUp() ?: super.onSupportNavigateUp()
     * }
     * ```
     */
    fun onSupportNavigateUp(): Boolean? {
        for (service in implementersHolder.implementations) {
            val value = service.onSupportNavigateUp()
            if (value != null) {
                return value
            }
        }
        return null
    }

    /**
     * Call this method from [AppCompatActivity.onCreate].
     */
    fun onCreate(savedInstanceState: Bundle?) {
        sideEffectPluginsManager.plugins.forEach { plugin ->
            setupSideEffectMediator(plugin)
            setupSideEffectImplementer(plugin)
        }
        implementersHolder.implementations.forEach { it.onCreate(savedInstanceState) }
    }

    /**
     * Call this method from [AppCompatActivity.onSaveInstanceState]
     */
    fun onSavedInstanceState(outState: Bundle) {
        implementersHolder.implementations.forEach { it.onSaveInstanceState(outState) }
    }

    /**
     * Call this method from [AppCompatActivity.onActivityResult]
     */
    fun onActivityResult(requestCode: Int, responseCode: Int, data: Intent?) {
        implementersHolder.implementations.forEach { it.onActivityResult(requestCode, responseCode, data) }
    }

    /**
     * Call this method from [AppCompatActivity.onRequestPermissionsResult]
     */
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, granted: IntArray) {
        implementersHolder.implementations.forEach { it.onRequestPermissionsResult(requestCode, permissions, granted) }
    }

    fun notifyScreenUpdates() {
        implementersHolder.implementations.forEach { it.onRequestUpdates() }
    }

    fun getBaseActivityViewModel(): BaseActivityViewModel {
        return activityViewModel
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        sideEffectPluginsManager.plugins.forEach {
            activityViewModel.sideEffectMediatorsHolder.setTargetWithPlugin(it, implementersHolder)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        activityViewModel.sideEffectMediatorsHolder.removeTargets()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        implementersHolder.clear()
    }

    private fun setupSideEffectMediator(plugin: SideEffectPlugin<*, *>) {
        val holder = activityViewModel.sideEffectMediatorsHolder

        if (!holder.contains(plugin.mediatorClass)) {
            holder.putWithPlugin(activity.applicationContext, plugin)
        }
    }

    private fun setupSideEffectImplementer(plugin: SideEffectPlugin<*, *>) {
        try {
            implementersHolder.putWithPlugin(plugin, activityViewModel.sideEffectMediatorsHolder, activity)
        } catch (ex: Exception) {}

    }
}