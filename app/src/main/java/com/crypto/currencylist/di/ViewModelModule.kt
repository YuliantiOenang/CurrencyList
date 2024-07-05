package com.crypto.currencylist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crypto.currencylist.AddCurrencyViewModel
import com.crypto.currencylist.CurrencyListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: CurrencyViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyListViewModel::class)
    abstract fun bindCurrencyViewModel(viewModel: CurrencyListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddCurrencyViewModel::class)
    abstract fun bindAddInstrumentViewModel(viewModel: AddCurrencyViewModel): ViewModel
}