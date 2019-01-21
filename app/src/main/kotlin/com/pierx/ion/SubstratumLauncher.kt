@file:Suppress("ConstantConditionIf")

package com.pierx.ion

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle

class SubstratumLauncher : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val returnIntent = if (intent.action == "projekt.substratum.GET_KEYS") {
            Intent("projekt.substratum.RECEIVE_KEYS")
        } else {
            Intent()
        }
        val themeName = getString(R.string.ThemeName)
        val themeAuthor = getString(R.string.ThemeAuthor)
        val themePid = packageName
        returnIntent.putExtra("theme_name", themeName)
        returnIntent.putExtra("theme_author", themeAuthor)
        returnIntent.putExtra("theme_pid", themePid)
        returnIntent.putExtra("theme_debug", BuildConfig.DEBUG)

        if (intent.action == "projekt.substratum.THEME") {
            setResult(getSelfSignature(applicationContext), returnIntent)
        } else if (intent.action == "projekt.substratum.GET_KEYS") {
            returnIntent.action = "projekt.substratum.RECEIVE_KEYS"
            sendBroadcast(returnIntent)
        }
        finish()
    }

    @Suppress("DEPRECATION")
    @SuppressLint("PackageManagerGetSignatures")
    fun getSelfSignature(context: Context): Int {
        try {
            val sigs = context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_SIGNATURES
            ).signatures
            return sigs[0].hashCode()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0
    }
}