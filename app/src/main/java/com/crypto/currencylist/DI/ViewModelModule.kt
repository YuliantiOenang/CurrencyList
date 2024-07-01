package com.crypto.currencylist.DI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crypto.currencylist.CurrencyListApplication
import com.crypto.currencylist.CurrencyListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: CurrencyViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyListViewModel::class)
    abstract fun bindCurrencyViewModel(viewModel: CurrencyListViewModel): ViewModel
}