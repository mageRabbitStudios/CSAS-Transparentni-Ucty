package com.kinzlstanislav.csasucty

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {

}

open class CSASApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule).androidContext(
                this@CSASApp
            )
        }
    }

}