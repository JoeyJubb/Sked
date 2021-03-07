package uk.co.bubblebearapps.presentation.ui

import android.graphics.drawable.Drawable

sealed class ViewState<out T> {

    /**
     * The view is ready to accept user input.
     *
     * @param data accompanying object that should sufficiently describe the view
     */
    data class Ready<T>(val data: T) : ViewState<T>()

    /**
     * Display a busy indicator, no user input should be accepted.
     */
    object Busy : ViewState<Nothing>()

    /**
     * Display an error message to the user with an optional action to take
     *
     * @param message message to display to the user
     * @param action the action to take to fix the error (can be null)
     */
    data class Error(
            val message: String,
            val action: Action? = null
    ) : ViewState<Nothing>()

    /**
     * Display an alert to the user that requires immediate action
     *
     * @param title This should briefly describe what will happen if the positiveAction is invoked. e.g. "You're about to delete some stuff!"
     * @param message This should go into a little more detail e.g. "You won't be able to get your stuff back"
     * @param icon optional small image to show on the dialog
     * @param positiveAction The "yes" or "ok" action
     * @param negativeAction optional "no" or "cancel" action
     * @param cancelledAction optional action to perform when dialog is cancelled
     */
    data class Alert(
            val title: String? = null,
            val message: String,
            val icon: Drawable? = null,
            val positiveAction: Action,
            val negativeAction: Action? = null,
            val cancelledAction: (() -> Unit)? = null
    ) : ViewState<Nothing>()
}

data class Action(
        val label: String,
        val invokable: () -> Unit
)