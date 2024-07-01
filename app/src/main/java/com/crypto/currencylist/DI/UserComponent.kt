package com.crypto.currencylist.DI

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
}