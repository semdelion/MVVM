package com.semdelion.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import com.semdelion.data.storages.account.AppSettings
import com.semdelion.presentation.core.sideeffects.SideEffectPluginsManager
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogsPlugin
import com.semdelion.presentation.core.sideeffects.intents.plugin.IntentsPlugin
import com.semdelion.presentation.core.sideeffects.navigator.plugin.NavigatorPlugin
import com.semdelion.presentation.core.sideeffects.navigator.plugin.StackFragmentNavigator
import com.semdelion.presentation.core.sideeffects.permissions.plugin.PermissionsPlugin
import com.semdelion.presentation.core.sideeffects.resources.plugin.ResourcesPlugin
import com.semdelion.presentation.core.sideeffects.toasts.plugin.ToastsPlugin
import com.semdelion.presentation.core.views.BaseActivity
import com.semdelion.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val args: MainActivityArgs by navArgs()

    private val viewModel by viewModels<MainActivityViewModel>()

    @Inject
    lateinit var toastsPlugin: ToastsPlugin
    @Inject
    lateinit var resourcesPlugin: ResourcesPlugin
    @Inject
    lateinit var navigatorPlugin: NavigatorPlugin
    @Inject
    lateinit var permissionsPlugin: PermissionsPlugin
    @Inject
    lateinit var dialogsPlugin: DialogsPlugin
    @Inject
    lateinit var intentsPlugin: IntentsPlugin


    override fun registerPlugins(manager: SideEffectPluginsManager) = with(manager) {
        register(toastsPlugin)
        register(resourcesPlugin)
        register(navigatorPlugin)
        register(permissionsPlugin)
        register(dialogsPlugin)
        register(intentsPlugin)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
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
}