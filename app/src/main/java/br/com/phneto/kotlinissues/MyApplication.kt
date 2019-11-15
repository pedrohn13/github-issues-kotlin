package br.com.phneto.kotlinissues

import android.app.Application
import appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
        }

    }

}