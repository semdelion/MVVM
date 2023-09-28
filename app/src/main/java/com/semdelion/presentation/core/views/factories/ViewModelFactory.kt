package com.semdelion.presentation.core.views.factories

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.semdelion.presentation.core.SingletonScopeDependencies
import com.semdelion.presentation.core.views.ActivityDelegateHolder
import com.semdelion.presentation.core.views.BaseFragment
import java.lang.reflect.Constructor


typealias ViewModelCreator<VM> = () -> VM
inline fun <reified VM : ViewModel> ComponentActivity.viewModelCreator(noinline creator: ViewModelCreator<VM>): Lazy<VM> {
    return viewModels { ActivityViewModelFactory(creator) }
}

class ActivityViewModelFactory<VM : ViewModel>(
    private val viewModelCreator: ViewModelCreator<VM>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelCreator() as T
    }
}

inline fun <reified VM : ViewModel> BaseFragment.viewModel() = viewModels<VM> {
    val application = requireActivity().application

    val baseActivityViewModel = (requireActivity() as ActivityDelegateHolder).delegate.getBaseActivityViewModel()

    // forming the list of available dependencies:
    // - singleton scope dependencies (repositories) -> from App class
    // - activity VM scope dependencies -> from ActivityScopeViewModel
    val dependencies = baseActivityViewModel.sideEffectMediators +
            SingletonScopeDependencies.getSingletonScopeDependencies(application)

    ViewModelFactory(dependencies, this, arguments)
}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val dependencies: List<Any>,
    owner: SavedStateRegistryOwner,
    args: Bundle?
) : AbstractSavedStateViewModelFactory(owner, args) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        val constructors = modelClass.constructors
        val constructor = constructors.maxByOrNull { it.typeParameters.size }!!
        // - SavedStateHandle is also a dependency from screen VM scope, but we can obtain it only here,
        //   that's why merging it with the list of other dependencies:
        val dependenciesWithSavedState = dependencies + handle

        // generating the list of arguments to be passed into the view-model's constructor
        val arguments = findDependencies(constructor, dependenciesWithSavedState)

        // creating view-model
        return constructor.newInstance(*arguments.toTypedArray()) as T
    }

    private fun findDependencies(constructor: Constructor<*>, dependencies: List<Any>): List<Any> {
        val args = mutableListOf<Any>()
        constructor.parameterTypes.forEach { parameterClass ->
            val dependency = dependencies.first { parameterClass.isAssignableFrom(it.javaClass) }
            args.add(dependency)
        }
        return args
    }
}