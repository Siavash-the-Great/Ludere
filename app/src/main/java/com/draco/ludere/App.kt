package com.draco.ludere

import ir.tapsell.sdk.Tapsell

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Tapsell.initialize(this, config, BuildConfig.TAPSELL_KEY)
    }
}
