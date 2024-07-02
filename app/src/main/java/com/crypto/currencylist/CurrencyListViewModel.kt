package com.crypto.currencylist

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.currencylist.data.CurrencyInfo
import com.crypto.currencylist.repository.CurrencyInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(var currencyInfoRepository: CurrencyInfoRepository): ViewModel(),
    Observable {
    var currencyList: MutableLiveData<MutableList<CurrencyInfo>> = MutableLiveData(mutableListOf())
    var state = State.IDLE
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
                    currencyList.value = currencies?.value?.toMutableList()
                    currencyList.postValue(currencies?.value?.toMutableList())
                    callbacks.notifyCallbacks(this@CurrencyListViewModel, BR.empty, null)
                }
            } catch (e: Exception) {
                Log.e("CurrencyListViewModel", "Error fetching currency", e)
            }
        }
    }

    @Bindable
    fun isEmpty(): Boolean {
        println("yulianti empty")
        return state == State.SEARCHING && currencyList.value?.isEmpty() ?: true
    }

    enum class State {
        SEARCHING, IDLE, EMPTY
    }

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }
}