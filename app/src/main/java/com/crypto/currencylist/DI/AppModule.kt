package com.crypto.currencylist.DI

import android.app.Application
import android.content.Context
import com.crypto.currencylist.CurrencyListApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun context(): Context {
        return application
    }
}