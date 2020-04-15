package com.babblingbrook.mtgcardsearch

import android.app.Application
import com.babblingbrook.mtgcardsearch.di.dataModule
import com.babblingbrook.mtgcardsearch.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class MTGCardSearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MTGCardSearchApp)
            modules(listOf(dataModule, uiModule))
        }
    }
}