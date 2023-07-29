package com.dhl.retrofitdownload.app

import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.CrashUtils

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        CrashUtils.init()

    }

    companion object {
        var context: Context? = null
        fun getImagePath(): String {
            return context?.cacheDir!!.path
        }
    }
}