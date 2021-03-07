package uk.co.bubblebearapps.presentation.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.ui.ViewState
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    private var dialog: AlertDialog? = null
        set(value) {
            field?.dismiss()
            field = value
        }

    private var snackBar: Snackbar? = null
        set(value) {
            field?.dismiss()
            field = value
        }

    override fun onPause() {
        super.onPause()
        clearDialog()
    }

    protected fun showAlert(alert: ViewState.Alert) {
        clearDialog()
        with(alert) {
            val builder = MaterialAlertDialogBuilder(requireContext())
                    .setTitle(title)
                    .setMessage(message)
                    .setIcon(icon)
                    .setPositiveButton(
                            positiveAction.label
                    ) { _, _ -> positiveAction.invokable() }

            negativeAction?.let {
                builder.setNegativeButton(
                        negativeAction.label
                ) { _, _ -> negativeAction.invokable() }
            }
            cancelledAction?.let {
                builder.setOnCancelListener { it() }
            }

            this@BaseFragment.dialog = builder.show()
        }
    }

    private fun clearDialog() {
        dialog = null
    }

    protected fun showError(viewState: ViewState.Error) {
        snackBar = Snackbar.make(requireView(), viewState.message, Snackbar.LENGTH_INDEFINITE)
                .also { snackBar ->
                    viewState.action?.let { action ->
                        snackBar.setAction(
                                action.label
                        ) { action.invokable() }
                    }

                    snackBar.show()
                }
    }
}
