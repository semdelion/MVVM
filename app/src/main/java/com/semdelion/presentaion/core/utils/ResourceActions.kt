package com.semdelion.presentaion.core.utils

import com.semdelion.domain.core.tasks.dispatchers.Dispatcher

typealias ResourceAction<T> = (T) -> Unit

class ResourceActions<T>(
    private val dispatcher: Dispatcher
) {
    var resource: T? = null
        set(newValue) {
            field = newValue
            if (newValue != null) {
                actions.forEach { action ->
                    dispatcher.dispatch {
                        action(newValue)
                    }
                }
                actions.clear()
            }
        }

    private val actions = mutableListOf<ResourceAction<T>>()

    /**
     * Invoke the action only when [resource] exists (not null). Otherwise
     * the action is postponed until some non-null value is assigned to [resource]
     */
    operator fun invoke(action: ResourceAction<T>) {
        val resource = this.resource
        if (resource == null) {
            actions += action
        } else {
            dispatcher.dispatch {
                action(resource)
            }
        }
    }

    fun clear() {
        actions.clear()
    }
}