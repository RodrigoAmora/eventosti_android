package br.com.rodrigoamora.eventosti.extension

import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView

//ProgressBar
fun ProgressBar.hide() {
    this.visibility = View.GONE
}

fun ProgressBar.show() {
    this.visibility = View.VISIBLE
}


//SearchView
fun SearchView.hide() {
    this.visibility = View.GONE
}

fun SearchView.show() {
    this.visibility = View.VISIBLE
}