package com.corootine

import android.app.Application
import timber.log.Timber

class FuzzyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}