package com.crypto.currencylist.di

import com.crypto.currencylist.feature.addcurrency.AddCurrencyActivity
import com.crypto.currencylist.feature.addcurrency.AddCurrencyFragment
import com.crypto.currencylist.feature.currencylist.CurrencyListFragment
import com.crypto.currencylist.feature.currencylist.DemoActivity
import dagger.Component

@UserScope
@Component(
    dependencies = [AppComponent::class],
    modules = [UserModule::class, ViewModelModule::class]
)
interface UserComponent {
    fun inject(activity: DemoActivity)
    fun inject(fragment: CurrencyListFragment)
    fun inject(fragment: AddCurrencyActivity)
    fun inject(fragment: AddCurrencyFragment)
}