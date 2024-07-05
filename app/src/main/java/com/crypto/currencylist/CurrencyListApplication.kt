package com.crypto.currencylist

import android.app.Application
import com.crypto.currencylist.DI.AppComponent
import com.crypto.currencylist.DI.AppModule
import com.crypto.currencylist.DI.DaggerAppComponent
import com.crypto.currencylist.DI.DaggerUserComponent
import com.crypto.currencylist.DI.UserComponent


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