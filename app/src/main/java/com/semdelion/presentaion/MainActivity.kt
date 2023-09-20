package com.semdelion.presentaion

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.semdelion.presentaion.core.sideeffects.SideEffectPluginsManager
import com.semdelion.presentaion.core.sideeffects.dialogs.plugin.DialogsPlugin
import com.semdelion.presentaion.core.sideeffects.intents.plugin.IntentsPlugin
import com.semdelion.presentaion.core.sideeffects.navigator.plugin.NavigatorPlugin
import com.semdelion.presentaion.core.sideeffects.navigator.plugin.StackFragmentNavigator
import com.semdelion.presentaion.core.sideeffects.permissions.plugin.PermissionsPlugin
import com.semdelion.presentaion.core.sideeffects.resources.plugin.ResourcesPlugin
import com.semdelion.presentaion.core.sideeffects.toasts.plugin.ToastsPlugin
import com.semdelion.presentaion.core.views.BaseActivity

class MainActivity : BaseActivity() {

    override fun registerPlugins(manager: SideEffectPluginsManager) = with (manager) {
        register(ToastsPlugin())
        register(ResourcesPlugin())
        register(NavigatorPlugin(createNavigator()))
        register(PermissionsPlugin())
        register(DialogsPlugin())
        register(IntentsPlugin())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Initializer.initDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContent) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    private fun createNavigator() = StackFragmentNavigator(
        containerId = R.id.fragmentContent,
        animations = StackFragmentNavigator.Animations(
            enterAnim = R.anim.enter,
            exitAnim = R.anim.exit,
            popEnterAnim = R.anim.pop_enter,
            popExitAnim = R.anim.pop_exit
        )
        //initialScreenCreator = { FirstFragment.Screen() } TODO
    )
}