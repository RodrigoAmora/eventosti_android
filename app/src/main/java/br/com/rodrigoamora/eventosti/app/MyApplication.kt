package br.com.rodrigoamora.eventosti.app

import android.app.Application
import br.com.rodrigoamora.eventosti.di.injectFeature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        this.instantiateKoin()
        this.instantiateApplicationScope()
    }

    override fun onTerminate() {
        super.onTerminate()
        this.terminateKoin()
    }

    private fun instantiateKoin() {
        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            injectFeature()
        }
    }

    private fun terminateKoin() {
        stopKoin()
    }

    private fun instantiateApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())

}
