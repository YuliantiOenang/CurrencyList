package com.crypto.currencylist.repository

import com.crypto.currencylist.data.local.CurrencyInfo

interface CurrencyInfoRepository {
    suspend fun getAllCurrencyLists(): List<CurrencyInfo>?
    suspend fun getAllCryptoCurrencyLists(): List<CurrencyInfo?>?
    suspend fun getAllFiatCurrencyLists(): List<CurrencyInfo?>?
    suspend fun insert(currencyInfo: CurrencyInfo): Long
    suspend fun deleteAllCurrencylist()
    suspend fun searchQuery(query: String): List<CurrencyInfo?>?
}