package com.semdelion.presentation.core.sideeffects.navigator.plugin

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.semdelion.presentation.core.sideeffects.SideEffectImplementation
import com.semdelion.presentation.core.sideeffects.navigator.utils.INavCommand
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.utils.Event
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.core.views.utils.HasScreenTitle
import com.semdelion.presentation.ui.tabs.TabsFragment
import java.util.regex.Pattern

private fun NavController.navigate(navCommand: INavCommand) {
    navCommand.launch(this)
}

class StackFragmentNavigator(
    @IdRes private val containersId: Set<Int>,
    @IdRes private val topLevelDestinationsId: Set<Int>
) : SideEffectImplementation(), Navigator {

    private var result: Event<Any>? = null

    private var currentFragment: Fragment? = null

    private var navController: NavController? = null

    override fun launch(navCommand: INavCommand) {
        launchDirections(navCommand)
    }

    override fun goBack(result: Any?) {
        if (result != null) {
            this.result = Event(result)
        }
        requireActivity().onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().lifecycle.addObserver(this)
        requireActivity().supportFragmentManager.registerFragmentLifecycleCallbacks(
            fragmentCallbacks,
            true
        )
    }

    override fun onBackPressed(): Boolean {
        val f = currentFragment
        if ((f is BaseFragment) && f.viewModel.onBackPressed()) {
            return true
        }

        return if (isStartDestination(navController?.currentDestination)) {
            super.onBackPressed()
        } else {
            navController?.popBackStack() ?: false
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        (navController?.navigateUp() ?: false) || (super.onSupportNavigateUp() ?: false)


    override fun onRequestUpdates() {
        val f = currentFragment

        if (f is HasScreenTitle && f.getScreenTitle() != null) {
            requireActivity().supportActionBar?.title = f.getScreenTitle()
        } else {

            navController?.currentDestination?.arguments
            requireActivity().supportActionBar?.title = prepareTitle(
                navController?.currentDestination?.label,
                f?.arguments
            )
        }

        requireActivity().supportActionBar?.setDisplayHomeAsUpEnabled(
            !isStartDestination(
                navController?.currentDestination
            )
        )
    }

    private val destinationListener =
        NavController.OnDestinationChangedListener { _, destination, arguments ->
            requireActivity().supportActionBar?.title = prepareTitle(destination.label, arguments)
            requireActivity().supportActionBar?.setDisplayHomeAsUpEnabled(
                !isStartDestination(
                    destination
                )
            )
        }

    private fun launchDirections(navCommand: INavCommand) : Boolean {
        //TODO need monitor right navController
        for (container in containersId) {
            try {
                val navController = requireActivity().findNavController(container)
                navController.navigate(navCommand)
                return true
            } catch (_: IllegalArgumentException) {
                //ignore error with wrong nav container
            }
        }
        return false
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is NavHostFragment || f is TabsFragment) return
            currentFragment = f
            onNavControllerActivated(f.findNavController())
            onRequestUpdates()
            publishResults(f)
        }
    }

    private fun onNavControllerActivated(navController: NavController) {
        if (this.navController == navController) return
        //this.navController?.removeOnDestinationChangedListener(destinationListener)
        //navController.addOnDestinationChangedListener(destinationListener)
        this.navController = navController
    }

    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        val graph = destination.parent ?: return false
        val startDestinations = topLevelDestinationsId + graph.startDestinationId
        return startDestinations.contains(destination.id)
    }

    private fun prepareTitle(label: CharSequence?, arguments: Bundle?): String {

        // code for this method has been copied from Google sources :)

        if (label == null) return ""
        val title = StringBuffer()
        val fillInPattern = Pattern.compile("\\{(.+?)\\}")
        val matcher = fillInPattern.matcher(label)
        while (matcher.find()) {
            val argName = matcher.group(1)
            if (arguments != null && arguments.containsKey(argName)) {
                matcher.appendReplacement(title, "")
                title.append(arguments[argName].toString())
            } else {
                throw IllegalArgumentException(
                    "Could not find $argName in $arguments to fill label $label"
                )
            }
        }
        matcher.appendTail(title)
        return title.toString()
    }

    private fun publishResults(fragment: Fragment) {
        val result = result?.get() ?: return
        if (fragment is BaseFragment) {
            // has result that can be delivered to the view-model
            fragment.viewModel.onResult(result)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        requireActivity().supportFragmentManager.unregisterFragmentLifecycleCallbacks(
            fragmentCallbacks
        )
        navController = null
    }
}