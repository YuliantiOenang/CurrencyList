package com.crypto.currencylist.feature.addcurrency

import android.util.Log
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

class AddCurrencyViewModel @javax.inject.Inject constructor(private var currencyInfoRepository: CurrencyInfoRepository) :
    ViewModel(),
    Observable {
    private val _callbacks: PropertyChangeRegistry = PropertyChangeRegistry()
    val currencyLD: MutableLiveData<Long> = MutableLiveData()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        _callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        _callbacks.remove(callback)
    }

    fun addCurrency(currencyInfo: CurrencyInfo?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                currencyInfo?.let {
                    val currency = currencyInfoRepository.insert(it)
                    withContext(Dispatchers.Main) {
                        currencyLD.postValue(currency)
                    }
                }
            } catch (e: Exception) {
                Log.e("AddInstrumentViewModel", "Error adding currency", e)
            }
        }
    }
}