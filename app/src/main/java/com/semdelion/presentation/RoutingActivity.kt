package com.semdelion.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.semdelion.presentation.core.sideeffects.SideEffectPluginsManager
import com.semdelion.presentation.core.utils.observeEvent
import com.semdelion.presentation.core.views.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutingActivity  : BaseActivity() {

    private val viewModel by viewModels<RoutingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        viewModel.launchMainScreenEvent.observeEvent(this) {result: Boolean -> launchMainScreen(result) }
    }

    private fun launchMainScreen(isSignedIn: Boolean) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val args = MainActivityArgs(isSignedIn)
        intent.putExtras(args.toBundle())
        startActivity(intent)
    }

    override fun registerPlugins(manager: SideEffectPluginsManager) { }
}