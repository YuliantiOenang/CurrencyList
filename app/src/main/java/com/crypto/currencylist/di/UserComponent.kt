package com.crypto.currencylist.di

import com.crypto.currencylist.AddCurrencyActivity
import com.crypto.currencylist.AddCurrencyFragment
import com.crypto.currencylist.CurrencyListFragment
import com.crypto.currencylist.DemoActivity
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