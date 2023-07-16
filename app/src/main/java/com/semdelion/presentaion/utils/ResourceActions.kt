package com.semdelion.presentaion.utils

typealias ResourceAction<T> = (T) -> Unit

class ResourceActions<T> {

    private val _actions = mutableListOf<ResourceAction<T>>()

    var resource: T? = null
        set(newValue) {
            field = newValue
            if (newValue != null) {
                _actions.forEach { it(newValue) }
                _actions.clear()
            }
        }

    operator fun invoke(action: ResourceAction<T>) {
        val resource = this.resource
        if (resource == null) {
            _actions += action
        } else {
            action(resource)
        }
    }

    fun clear() {
        _actions.clear()
    }
}