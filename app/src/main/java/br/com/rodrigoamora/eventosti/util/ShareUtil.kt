package br.com.rodrigoamora.eventosti.util

import android.content.Context
import android.content.Intent


class ShareUtil {
    companion object {
        fun directShare(context: Context, title: String?, text: String?) {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            //sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            sharingIntent.setType("text/plain")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, text)

            context.startActivity(Intent.createChooser(sharingIntent, title))
        }
    }
}