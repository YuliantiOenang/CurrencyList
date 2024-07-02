package com.crypto.currencylist.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.crypto.currencylist.data.AppDatabase
import com.crypto.currencylist.data.CurrencyInfo
import java.util.concurrent.ExecutorService

class LocalDataStorage(var application: Context): CurrencyInfoRepository {
    private val db = AppDatabase.getDatabase(application)
    private var allCurrencyInfos: MutableLiveData<List<CurrencyInfo>> = MutableLiveData<List<CurrencyInfo>>()
    private var firstItem: CurrencyInfo? = null

    override suspend fun getAllCurrencyLists(): MutableLiveData<List<CurrencyInfo>> {
        allCurrencyInfos.postValue(db?.currencyInfoDao()?.all)
        return allCurrencyInfos
    }

    override suspend fun getAllCryptoCurrencyLists(): MutableLiveData<List<CurrencyInfo>>? {
        db?.currencyInfoDao()?.loadAllCrypto()?.let {
            allCurrencyInfos.postValue(it as List<CurrencyInfo>?)
        }
        return allCurrencyInfos
    }

    override suspend fun getAllFiatCurrencyLists(): MutableLiveData<List<CurrencyInfo>>? {
        db?.currencyInfoDao()?.loadAllFiat()?.let {
            allCurrencyInfos.postValue(it as List<CurrencyInfo>?)
        }
        return allCurrencyInfos
    }

    override suspend fun insert(currencyInfo: CurrencyInfo?) {
        db?.currencyInfoDao()?.insertAll(currencyInfo)
    }

    override suspend fun deleteAllCurrencylist() {
        db?.currencyInfoDao()?.delete()
    }

    override suspend fun searchQuery(query: String): MutableLiveData<List<CurrencyInfo>>? {
        db?.currencyInfoDao()?.findByName(query)?.let {
            allCurrencyInfos.postValue(it)
        }
        return allCurrencyInfos
    }
}