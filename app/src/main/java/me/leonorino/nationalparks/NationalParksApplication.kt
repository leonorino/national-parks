package me.leonorino.nationalparks

import android.app.Application
import me.leonorino.nationalparks.data.di.AppContainer

class NationalParksApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}