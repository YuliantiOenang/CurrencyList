package com.crypto.currencylist.di

import android.content.Context
import com.crypto.currencylist.data.local.AppDatabase
import com.crypto.currencylist.data.local.CurrencyInfoDao
import com.crypto.currencylist.repository.CurrencyInfoRepository
import com.crypto.currencylist.repository.LocalDataStore
import dagger.Module
import dagger.Provides

@Module
class UserModule {

    @UserScope
    @Provides
    fun provideDatabase(application: Context): AppDatabase {
        return AppDatabase.getDatabase(application)!!
    }

    @UserScope
    @Provides
    fun provideCurrencyInfoDao(database: AppDatabase): CurrencyInfoDao {
        return database.currencyInfoDao()
    }


    @UserScope
    @Provides
    fun provideDbCurrencyInfoRepository(currencyInfoDao: CurrencyInfoDao): CurrencyInfoRepository {
        return LocalDataStore(currencyInfoDao)
    }
}