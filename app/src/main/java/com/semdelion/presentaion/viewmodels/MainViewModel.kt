package com.semdelion.presentaion.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.semdelion.presentaion.App
import com.semdelion.presentaion.MainActivity
import com.semdelion.presentaion.R
import com.semdelion.presentaion.navigation.Navigator
import com.semdelion.presentaion.navigation.UiActions
import com.semdelion.presentaion.utils.Event
import com.semdelion.presentaion.utils.ResourceActions
import com.semdelion.presentaion.viewmodels.base.LiveEvent
import com.semdelion.presentaion.viewmodels.base.MutableLiveEvent
import com.semdelion.presentaion.views.base.BaseScreen

const val ARG_SCREEN = "ARG_SCREEN"

class MainViewModel(
    application: Application
) : AndroidViewModel(application), Navigator, UiActions {

    val whenActivityActive = ResourceActions<MainActivity>()

    private val _result = MutableLiveEvent<Any>()
    val result: LiveEvent<Any> = _result

    override fun launch(screen: BaseScreen) = whenActivityActive {
        launchFragment(it, screen)
    }

    override fun goBack(result: Any?) = whenActivityActive {
        if (result != null) {
            _result.value = Event(result)
        }
        it.onBackPressed()
    }

    override fun toast(message: String) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messageRes: Int, vararg args: Any): String {
        return getApplication<App>().getString(messageRes, *args)
    }

    fun launchFragment(activity: MainActivity, screen: BaseScreen, addToBackStack: Boolean = true) {
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to screen)
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack)
            transaction.addToBackStack(null)
        transaction.setCustomAnimations(
            R.anim.enter,
            R.anim.exit,
            R.anim.pop_enter,
            R.anim.pop_exit
        )
            .replace(R.id.fragmentContent, fragment)
            .commit()
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
    }
}