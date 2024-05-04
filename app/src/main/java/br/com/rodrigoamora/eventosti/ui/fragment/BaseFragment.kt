package br.com.rodrigoamora.eventosti.ui.fragment

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.rodrigoamora.eventosti.R
import com.google.android.material.snackbar.Snackbar

open class BaseFragment: Fragment() {
    fun showError(context: Context, responseCode: Int) {
        val message: String = when (responseCode) {
            403 -> {
                getString(R.string.error_access_denied)
            }

            404 -> {
                getString(R.string.error_cant_was_possible_find_event)
            }

            in 500..599 -> {
                getString(R.string.error_service_unavailable)
            }

            else -> {
                getString(R.string.error_cant_was_possible_perform_operation)
            }
        }

        this.showToast(context, message)
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showSnackBar(message: String, rootView: View) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }

}
