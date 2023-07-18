package com.semdelion.presentaion.core.navigator

import android.os.Bundle
import android.view.View
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.semdelion.presentaion.core.utils.Event
import com.semdelion.presentaion.core.ARG_SCREEN
import com.semdelion.presentaion.core.views.BaseFragment
import com.semdelion.presentaion.core.views.utils.BaseScreen
import com.semdelion.presentaion.core.views.utils.HasScreenTitle

class FragmentNavigator(
    private val activity: AppCompatActivity,
    @IdRes private val containerId: Int,
    private val animations: Animations,
    private val initialScreenCreator: () -> BaseScreen,
) : Navigator {

    private var _result: Event<Any>? = null

    override fun launch(screen: BaseScreen) {
        launchFragment(screen)
    }

    override fun goBack(result: Any?) {
        if (result != null) {
            _result = Event(result)
        }
        activity.onBackPressed()
    }

    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            launchFragment(
                screen = initialScreenCreator(),
                addToBackStack = false
            )
        }
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    fun onDestroy() {
        activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    private fun launchFragment(screen: BaseScreen, addToBackStack: Boolean = true) {
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to screen)
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack)
            transaction.addToBackStack(null)
        transaction.setCustomAnimations(
            animations.enterAnim,
            animations.exitAnim,
            animations.popEnter,
            animations.popExit
        )
            .replace(containerId, fragment)
            .commit()
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?
        ) {
            notifyScreenUpdate()
            publishResults(f)
        }
    }

    private fun publishResults(fragment: Fragment) {
        val result = _result?.getValue() ?: return
        if(fragment is BaseFragment) {
            fragment.viewModel.onResult(result)
        }
    }

    fun notifyScreenUpdate() {
        val container = activity.supportFragmentManager.findFragmentById(containerId)
        val hasStack = activity.supportFragmentManager.backStackEntryCount > 0
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(hasStack)

        if((container is HasScreenTitle) && (container.getScreenTitle() != null)) {
            activity.supportActionBar?.title = container.getScreenTitle()
        } else {
            activity.supportActionBar?.title = ""
        }
    }

    data class Animations(
        @AnimRes val enterAnim:Int,
        @AnimRes val exitAnim:Int,
        @AnimRes val popEnter:Int,
        @AnimRes val popExit:Int
    )
}