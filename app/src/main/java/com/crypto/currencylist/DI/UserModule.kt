package com.crypto.currencylist.DI

import android.app.Application
import android.content.Context
import com.crypto.currencylist.CurrencyListApplication
import com.crypto.currencylist.repository.CurrencyInfoRepository
import com.crypto.currencylist.repository.LocalDataStorage
import dagger.Module
import dagger.Provides

@Module
class UserModule {

    @UserScope
    @Provides
    fun provideDbCurrencyInfoRepository(application: Context): CurrencyInfoRepository {
        return LocalDataStorage(application)
    }
}