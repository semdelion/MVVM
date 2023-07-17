package com.semdelion.presentaion

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.semdelion.presentaion.core.viewmodels.MainViewModel
import com.semdelion.presentaion.core.views.BaseFragment
import com.semdelion.presentaion.views.FirstFragment
import com.semdelion.presentaion.core.views.utils.HasScreenTitle

class MainActivity : AppCompatActivity() {

    private val activityViewModel by viewModels<MainViewModel> {
        ViewModelProvider.AndroidViewModelFactory(
            application
        )
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?
        ) {
            notifyScreenUpdate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            activityViewModel.launchFragment(
                activity = this,
                screen = FirstFragment.Screen(),
                addToBackStack = false
            )
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.whenActivityActive.resource = this
    }

    override fun onPause() {
        super.onPause()
        activityViewModel.whenActivityActive.resource = null
    }

    fun notifyScreenUpdate() {
        val container = supportFragmentManager.findFragmentById(R.id.fragmentContent)
        val hasStack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar?.setDisplayHomeAsUpEnabled(hasStack)

        if((container is HasScreenTitle) && (container.getScreenTitle() != null)) {
            supportActionBar?.title = container.getScreenTitle()
        } else {
            supportActionBar?.title = ""
        }

        val result = activityViewModel.result.value?.getValue() ?: return
        if(container is BaseFragment) {
            container.viewModel.onResult(result)
        }
    }
}