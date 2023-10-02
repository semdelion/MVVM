package com.semdelion.presentation

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import com.semdelion.presentation.core.sideeffects.SideEffectPluginsManager
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogsPlugin
import com.semdelion.presentation.core.sideeffects.intents.plugin.IntentsPlugin
import com.semdelion.presentation.core.sideeffects.navigator.plugin.NavigatorPlugin
import com.semdelion.presentation.core.sideeffects.navigator.plugin.StackFragmentNavigator
import com.semdelion.presentation.core.sideeffects.permissions.plugin.PermissionsPlugin
import com.semdelion.presentation.core.sideeffects.resources.plugin.ResourcesPlugin
import com.semdelion.presentation.core.sideeffects.toasts.plugin.ToastsPlugin
import com.semdelion.presentation.core.views.BaseActivity
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private val args: MainActivityArgs by navArgs()

    private val viewModel by viewModel<MainActivityViewModel>()

    override fun registerPlugins(manager: SideEffectPluginsManager) = with(manager) {
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
        val binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setSupportActionBar(binding.toolbar)
        setStartDestinationForNavController()
    }

    private fun setStartDestinationForNavController() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHost.navController

        val graph = navController.navInflater.inflate(R.navigation.main_graph)
        graph.setStartDestination(
            if (args.isSignedIn) {
                R.id.tabsFragment
            } else {
                R.id.signInFragment
            }
        )
        navController.graph = graph
    }

    private fun createNavigator() = StackFragmentNavigator(
        containersId = setOf(R.id.fragment_container, R.id.tabs_fragment_container),
        topLevelDestinationsId = setOf(R.id.tabsFragment, R.id.signInFragment),
        animations = StackFragmentNavigator.Animations(
            enterAnim = R.anim.enter,
            exitAnim = R.anim.exit,
            popEnterAnim = R.anim.pop_enter,
            popExitAnim = R.anim.pop_exit
        )
    )
}