package br.com.rodrigoamora.eventosti.extension

import android.view.View
import android.widget.ProgressBar

//ProgressBar
fun ProgressBar.hide() {
    this.visibility = View.GONE
}

fun ProgressBar.show() {
    this.visibility = View.VISIBLE
}
