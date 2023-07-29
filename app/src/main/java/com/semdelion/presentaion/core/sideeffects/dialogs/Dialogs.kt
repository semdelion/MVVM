package com.semdelion.presentaion.core.sideeffects.dialogs

import com.semdelion.domain.core.tasks.Task
import com.semdelion.presentaion.core.sideeffects.dialogs.plugin.DialogConfig

/**
 * Side-effects interface for managing dialogs from view-model.
 * You need to add [DialogsPlugin] to your activity before using this feature.
 *
 * WARN! Please note, dialogs don't survive after app killing.
 */
interface Dialogs {

    /**
     * Show alert dialog to the user and wait for the user choice.
     */
    fun show(dialogConfig: DialogConfig): Task<Boolean>

}