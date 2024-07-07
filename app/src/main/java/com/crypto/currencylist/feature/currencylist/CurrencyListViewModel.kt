package com.crypto.currencylist.feature.currencylist

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.currencylist.BR
import com.crypto.currencylist.data.local.CurrencyInfo
import com.crypto.currencylist.repository.CurrencyInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(private var currencyInfoRepository: CurrencyInfoRepository) :
    ViewModel(),
    Observable {
    var currencyList: MutableLiveData<List<CurrencyInfo>> = MutableLiveData(listOf())
    private var firstItem: MutableLiveData<CurrencyInfo?> = MutableLiveData()
    var state = State.IDLE
        set(value) {
            field = value
            callbacks.notifyChange(this, BR.empty)
        }

    init {
        getAllCurrencyLists()
    }

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    fun getAllCurrencyLists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currencies = currencyInfoRepository.getAllCurrencyLists()
                withContext(Dispatchers.Main) {
                    println("yulianti update currency list from getAllCurrencyLists")
                    firstItem.value = currencies?.firstOrNull()
                    currencyList.value = currencies?.toList()
                    callbacks.notifyChange(this@CurrencyListViewModel, BR.empty)
                    callbacks.notifyChange(this@CurrencyListViewModel, BR.recommendation)
                }
            } catch (e: Exception) {
                Log.e("CurrencyListViewModel", "Error fetching currency", e)
            }
        }
    }

    fun getAllCryptoCurrencyLists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currencies = currencyInfoRepository.getAllCryptoCurrencyList()
                withContext(Dispatchers.Main) {
                    currencies?.let { currencyList.postValue(it) }
                }
            } catch (e: Exception) {
                Log.e("CurrencyListViewModel", "Error fetching currency", e)
            }
        }
    }

    fun getAllFiatCurrencyLists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currencies = currencyInfoRepository.getAllFiatCurrencyList()
                withContext(Dispatchers.Main) {
                    currencies?.let { currencyList.postValue(it) }
                }
            } catch (e: Exception) {
                Log.e("CurrencyListViewModel", "Error fetching currency", e)
            }
        }
    }

    fun deleteAllCurrencyList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                currencyInfoRepository.deleteAllCurrencyList()
                val currencies = currencyInfoRepository.getAllCurrencyLists()
                withContext(Dispatchers.Main) {
                    currencies?.let { currencyList.postValue(it) }
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
                    currencies?.let { currencyList.postValue(it) }
                    callbacks.notifyChange(this@CurrencyListViewModel, BR.empty)
                    callbacks.notifyChange(this@CurrencyListViewModel, BR.recommendation)

                }
            } catch (e: Exception) {
                Log.e("CurrencyListViewModel", "Error fetching currency", e)
            }
        }
    }

    @Bindable
    fun getEmpty(): Boolean {
        return state == State.SEARCHING && currencyList.value?.isEmpty() ?: true
    }

    @Bindable
    fun getRecommendation(): CurrencyInfo? {
        return firstItem.value
    }

    enum class State {
        SEARCHING, IDLE
    }
}