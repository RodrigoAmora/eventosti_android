package br.com.rodrigoamora.eventosti.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

class PackageInfoUtil {
    companion object {
        fun getVersionName(context: Context): String? {
            return try {
                val packageManager: PackageManager = context.getPackageManager()
                val pInfo: PackageInfo = packageManager.getPackageInfo(context.getPackageName(), 0)
                pInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                ""
            }
        }
    }
}