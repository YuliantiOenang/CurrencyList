package com.crypto.currencylist.repository

import com.crypto.currencylist.data.local.CurrencyInfo
import com.crypto.currencylist.data.local.CurrencyInfoDao

class LocalDataStore(private val currencyInfoDao: CurrencyInfoDao): CurrencyInfoRepository {

    override suspend fun getAllCurrencyLists(): List<CurrencyInfo>? {
        return currencyInfoDao.all
    }

    override suspend fun getAllCryptoCurrencyLists(): List<CurrencyInfo?>? {
        return currencyInfoDao.loadAllCrypto()
    }

    override suspend fun getAllFiatCurrencyLists(): List<CurrencyInfo?>? {
        return  currencyInfoDao.loadAllFiat()
    }

    override suspend fun insert(currencyInfo: CurrencyInfo): Long {
        return currencyInfoDao.insert(currencyInfo)
    }

    override suspend fun deleteAllCurrencylist() {
        currencyInfoDao.delete()
    }

    override suspend fun searchQuery(query: String): List<CurrencyInfo?>? {
        return currencyInfoDao.findByName(query)
    }
}