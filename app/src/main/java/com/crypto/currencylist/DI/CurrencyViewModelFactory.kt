package com.crypto.currencylist.DI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crypto.currencylist.CurrencyListViewModel
import com.crypto.currencylist.repository.CurrencyInfoRepository
import javax.inject.Inject
import javax.inject.Provider

class CurrencyViewModelFactory @Inject constructor(
    private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = viewModels[modelClass]
            ?: viewModels.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}