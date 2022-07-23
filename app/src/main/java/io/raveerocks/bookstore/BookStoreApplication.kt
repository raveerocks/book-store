package io.raveerocks.bookstore

import android.app.Application
import timber.log.Timber

class BookStoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(object : Timber.DebugTree() {
            override fun log(
                priority: Int, tag: String?, message: String, t: Throwable?
            ) {
                super.log(priority, "App/$tag", message, t)
            }
        })
    }
}