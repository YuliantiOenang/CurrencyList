package com.crypto.currencylist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crypto.currencylist.DI.UserComponent
import com.crypto.currencylist.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userComponent = (this.applicationContext as CurrencyListApplication).userComponent
        if (!inject(userComponent)) {
            userComponent.inject(this)
        }
        binding = ActivityDemoBinding.inflate(layoutInflater)

        val fragment = CurrencyListFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        binding.btnAddInstrument.setOnClickListener {
            (fragment as? CurrencyListFragment)?.addItem()
        }

        binding.btnToCrypto.setOnClickListener {
            (fragment as? CurrencyListFragment)?.toCrypto()
        }

        binding.toFiat.setOnClickListener {
            (fragment as? CurrencyListFragment)?.toFiat()
        }

        binding.btnShowAll.setOnClickListener {
            (fragment as? CurrencyListFragment)?.showAll()
        }

        binding.btnClearList.setOnClickListener {
            (fragment as? CurrencyListFragment)?.clearList()
        }
        setContentView(binding.root)


    }

    open fun inject(component: UserComponent): Boolean {
        return false
    }
}