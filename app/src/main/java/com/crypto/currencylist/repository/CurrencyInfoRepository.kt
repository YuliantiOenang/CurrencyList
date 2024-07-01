package com.crypto.currencylist.repository

import androidx.lifecycle.MutableLiveData
import com.crypto.currencylist.data.CurrencyInfo


interface CurrencyInfoRepository {
    suspend fun getAllCurrencyLists(): MutableLiveData<List<CurrencyInfo>>?
    suspend fun getAllCryptoCurrencyLists(): MutableLiveData<List<CurrencyInfo>>?
    suspend fun getAllFiatCurrencyLists(): MutableLiveData<List<CurrencyInfo>>?
    suspend fun insert(currencyInfo: CurrencyInfo?)
    suspend fun deleteAllCurrencylist()
}