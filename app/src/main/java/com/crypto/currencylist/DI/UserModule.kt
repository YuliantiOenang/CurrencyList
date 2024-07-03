package com.crypto.currencylist.DI

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.crypto.currencylist.CurrencyListApplication
import com.crypto.currencylist.data.AppDatabase
import com.crypto.currencylist.data.CurrencyInfoDao
import com.crypto.currencylist.repository.CurrencyInfoRepository
import com.crypto.currencylist.repository.LocalDataStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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
        return LocalDataStorage(currencyInfoDao)
    }
}