package com.crypto.currencylist

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.currencylist.data.local.CurrencyInfo
import com.crypto.currencylist.repository.CurrencyInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(var currencyInfoRepository: CurrencyInfoRepository) :
    ViewModel(),
    Observable {
    var currencyList: MutableLiveData<MutableList<CurrencyInfo>> = MutableLiveData(mutableListOf())
    var firstItem: MutableLiveData<CurrencyInfo> = MutableLiveData()
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
                    firstItem.value = currencies?.first()
                    (currencies as? List<CurrencyInfo>)?.toMutableList()?.let { currencyList.postValue(it) }
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
                val currencies = currencyInfoRepository.getAllCryptoCurrencyLists()
                withContext(Dispatchers.Main) {
                    (currencies as? List<CurrencyInfo>)?.toMutableList()?.let { currencyList.postValue(it) }
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
                    (currencies as? List<CurrencyInfo>)?.toMutableList()?.let { currencyList.postValue(it) }
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
                    (currencies as? List<CurrencyInfo>)?.toMutableList()?.let { currencyList.postValue(it) }
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
                    (currencies as? List<CurrencyInfo>)?.toMutableList()?.let { currencyList.postValue(it) }
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
        SEARCHING, IDLE, EMPTY
    }
}