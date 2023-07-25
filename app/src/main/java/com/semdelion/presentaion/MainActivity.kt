package com.semdelion.presentaion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.semdelion.presentaion.core.navigator.FragmentNavigator
import com.semdelion.presentaion.core.navigator.IntermediateNavigator
import com.semdelion.presentaion.core.uiactions.UiActionsImpl
import com.semdelion.presentaion.core.utils.viewModelCreator
import com.semdelion.presentaion.core.BaseActivityViewModel
import com.semdelion.presentaion.core.views.utils.FragmentsHolder
import com.semdelion.presentaion.views.FirstFragment

class MainActivity : AppCompatActivity(), FragmentsHolder {

    private lateinit var navigator: FragmentNavigator

    private val activityViewModel by viewModelCreator<BaseActivityViewModel> {
        BaseActivityViewModel(
            uiActions = UiActionsImpl(application),
            navigator = IntermediateNavigator()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator = FragmentNavigator(
            activity = this,
            containerId = R.id.fragmentContent,
            animations = FragmentNavigator.Animations(
                enterAnim = R.anim.enter,
                exitAnim = R.anim.exit,
                popEnter = R.anim.pop_enter,
                popExit = R.anim.pop_exit),
            initialScreenCreator = { FirstFragment.Screen()}
        )
        navigator.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        navigator.onDestroy()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.navigator.setTarget(navigator)
    }

    override fun onPause() {
        super.onPause()
        activityViewModel.navigator.setTarget(null)
    }

    override fun notifyScreenUpdate() {
        navigator.notifyScreenUpdate()
    }

    override fun getBaseActivityViewModel(): BaseActivityViewModel {
        return activityViewModel
    }

    override fun onBackPressed() {
        navigator.onBackPressed()
        super.onBackPressed()
    }
}