package com.crypto.currencylist

import android.app.Application
import com.crypto.currencylist.di.AppComponent
import com.crypto.currencylist.di.AppModule
import com.crypto.currencylist.di.DaggerAppComponent
import com.crypto.currencylist.di.DaggerUserComponent
import com.crypto.currencylist.di.UserComponent


class CurrencyListApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    var userComponent: UserComponent =
        DaggerUserComponent
            .builder()
            .appComponent(appComponent)
            .build()
}