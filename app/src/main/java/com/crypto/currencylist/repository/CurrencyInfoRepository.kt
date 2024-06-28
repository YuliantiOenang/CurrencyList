package com.crypto.currencylist.repository

import com.crypto.currencylist.data.local.CurrencyInfo

interface CurrencyInfoRepository {
    suspend fun getAllCurrencyLists(): List<CurrencyInfo>?
    suspend fun getAllCryptoCurrencyList(): List<CurrencyInfo>?
    suspend fun getAllFiatCurrencyList(): List<CurrencyInfo>?
    suspend fun insert(currencyInfo: CurrencyInfo): Long
    suspend fun deleteAllCurrencyList()
    suspend fun searchQuery(query: String): List<CurrencyInfo>?
}