package com.crypto.currencylist.repository

import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import com.crypto.currencylist.data.CurrencyInfo


interface CurrencyInfoRepository {
    suspend fun getAllCurrencyLists(): List<CurrencyInfo>?
    suspend fun getAllCryptoCurrencyLists(): List<CurrencyInfo?>?
    suspend fun getAllFiatCurrencyLists(): List<CurrencyInfo?>?
    suspend fun insert(currencyInfo: CurrencyInfo?)
    suspend fun deleteAllCurrencylist()
    suspend fun searchQuery(query: String): List<CurrencyInfo?>?
}