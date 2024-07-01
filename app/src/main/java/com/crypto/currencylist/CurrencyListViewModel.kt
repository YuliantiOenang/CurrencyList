package com.crypto.currencylist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.currencylist.data.CurrencyInfo
import com.crypto.currencylist.repository.CurrencyInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(var currencyInfoRepository: CurrencyInfoRepository): ViewModel() {
    var currencyList: MutableLiveData<MutableList<CurrencyInfo>> = MutableLiveData(mutableListOf())
    init {
        getAllCurrencyLists()
    }
    fun getAllCurrencyLists(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currencies = currencyInfoRepository.getAllCurrencyLists()
                withContext(Dispatchers.Main) {
                    currencyList.postValue(currencies?.value?.toMutableList())
                }
            } catch (e: Exception) {
                Log.e("CurrencyListViewModel", "Error fetching currency", e)
            }
        }
    }
    fun getAllCryptoCurrencyLists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currencies = currencyInfoRepository.getAllCryptoCurrencyLists()
                withContext(Dispatchers.Main) {
                    currencyList.postValue(currencies?.value?.toMutableList())
                }
            } catch (e: Exception) {
                Log.e("CurrencyListViewModel", "Error fetching currency", e)
            }
        }
    }
    fun getAllFiatCurrencyLists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currencies = currencyInfoRepository.getAllFiatCurrencyLists()
                withContext(Dispatchers.Main) {
                    currencyList.postValue(currencies?.value?.toMutableList())
                }
            } catch (e: Exception) {
                Log.e("CurrencyListViewModel", "Error fetching currency", e)
            }
        }
    }
    fun insert(currencyInfo: CurrencyInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyInfoRepository.insert(currencyInfo)
        }
    }
    fun deleteAllCurrencylist() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                currencyInfoRepository.deleteAllCurrencylist()
                val currencies = currencyInfoRepository.getAllCurrencyLists()
                withContext(Dispatchers.Main) {
                    currencyList.postValue(currencies?.value?.toMutableList())
                }
            } catch (e: Exception) {
                Log.e("CurrencyListViewModel", "Error fetching currency", e)
            }
        }
    }

    fun searchName(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currencies = currencyInfoRepository.searchQuery(query)
                withContext(Dispatchers.Main) {
                    currencyList.postValue(currencies?.value?.toMutableList())
                }
            } catch (e: Exception) {
                Log.e("CurrencyListViewModel", "Error fetching currency", e)
            }
        }
    }
}