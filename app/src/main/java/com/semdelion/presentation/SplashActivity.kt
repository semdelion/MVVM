package com.semdelion.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.semdelion.presentation.core.sideeffects.SideEffectPluginsManager
import com.semdelion.presentation.core.views.BaseActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Initializer.initDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun registerPlugins(manager: SideEffectPluginsManager) {

    }
}