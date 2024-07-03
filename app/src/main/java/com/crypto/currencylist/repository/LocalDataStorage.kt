package com.crypto.currencylist.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.crypto.currencylist.data.AppDatabase
import com.crypto.currencylist.data.CurrencyInfo
import com.crypto.currencylist.data.CurrencyInfoDao
import java.util.concurrent.ExecutorService

class LocalDataStorage(private val currencyInfoDao: CurrencyInfoDao): CurrencyInfoRepository {
    private var allCurrencyInfos: List<CurrencyInfo> = listOf()
    private var firstItem: CurrencyInfo? = null

    override suspend fun getAllCurrencyLists(): List<CurrencyInfo>? {
        return currencyInfoDao.all
    }

    override suspend fun getAllCryptoCurrencyLists(): List<CurrencyInfo?>? {
        return currencyInfoDao.loadAllCrypto()
    }

    override suspend fun getAllFiatCurrencyLists(): List<CurrencyInfo?>? {
        return  currencyInfoDao.loadAllFiat()
    }

    override suspend fun insert(currencyInfo: CurrencyInfo?) {
        currencyInfoDao.insertAll(currencyInfo)
    }

    override suspend fun deleteAllCurrencylist() {
        currencyInfoDao.delete()
    }

    override suspend fun searchQuery(query: String): List<CurrencyInfo?>? {
        return currencyInfoDao.findByName(query)
    }
}