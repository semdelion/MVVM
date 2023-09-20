package com.semdelion.presentaion.core.sideeffects.navigator.plugin

import android.os.Bundle
import android.view.View
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.semdelion.presentaion.core.sideeffects.SideEffectImplementation
import com.semdelion.presentaion.core.sideeffects.navigator.Navigator
import com.semdelion.presentaion.core.utils.Event
import com.semdelion.presentaion.core.views.BaseFragment
import com.semdelion.presentaion.core.views.utils.HasScreenTitle

class StackFragmentNavigator(
    @IdRes private val containerId: Int,
    private val animations: Animations
) : SideEffectImplementation(), Navigator {

    private var result: Event<Any>? = null

    private var currentFragment: Fragment ?= null

    override fun launch(direction: NavDirections) {
        launchDirections(direction)
    }

    override fun goBack(result: Any?) {
        if (result != null) {
            this.result = Event(result)
        }
        requireActivity().onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().lifecycle.addObserver(this)
        requireActivity().supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, true)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        requireActivity().supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    override fun onBackPressed(): Boolean {
        val f = currentFragment
        return if (f is BaseFragment) {
            f.viewModel.onBackPressed()
        } else {
            false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        requireActivity().onBackPressed()
        return true
    }

    override fun onRequestUpdates() {
        val f = currentFragment
        if (f is HasScreenTitle && f.getScreenTitle() != null) {
            requireActivity().supportActionBar?.title = f.getScreenTitle()
        } 
    }

    private fun launchDirections(direction: NavDirections) {
        requireActivity().findNavController(containerId).navigate(
            directions = direction,
            navOptions =  navOptions {
                anim {
                    enter = animations.enterAnim
                    exit = animations.exitAnim
                    popEnter = animations.popEnterAnim
                    popExit = animations.popExitAnim
                }
            }
        )
    }

    private fun launchDestination(destination: Int, args: Bundle?) {
        requireActivity().findNavController(containerId).navigate(
            destination,
            args = args,
            navOptions =  navOptions {
                anim {
                    enter = animations.enterAnim
                    exit = animations.exitAnim
                    popEnter = animations.popEnterAnim
                    popExit = animations.popExitAnim
                }
            }
        )
    }

    private fun publishResults(fragment: Fragment) {
        val result = result?.getValue() ?: return
        if (fragment is BaseFragment) {
            // has result that can be delivered to the view-model
            fragment.viewModel.onResult(result)
        }
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            if (f is NavHostFragment) return
            currentFragment = f
            onRequestUpdates()
            publishResults(f)
        }
    }

    class Animations(
        @AnimRes val enterAnim: Int,
        @AnimRes val exitAnim: Int,
        @AnimRes val popEnterAnim: Int,
        @AnimRes val popExitAnim: Int,
    )
}