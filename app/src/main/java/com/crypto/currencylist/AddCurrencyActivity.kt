package com.crypto.currencylist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.crypto.currencylist.AddCurrencyActivity.companion.ADD_RESULT
import com.crypto.currencylist.di.UserComponent
import com.crypto.currencylist.databinding.ActivityAddCurrencyBinding
import javax.inject.Inject

class AddCurrencyActivity: AppCompatActivity() {
    object companion {
        const val ADD_RESULT = "ADD_RESULT"
    }
    private lateinit var binding: ActivityAddCurrencyBinding
    private lateinit var fragment: AddCurrencyFragment
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userComponent = (this.applicationContext as CurrencyListApplication).userComponent
        if (!inject(userComponent)) {
            userComponent.inject(this)
        }

        val viewModel = ViewModelProvider(this, viewModelFactory).get(AddCurrencyViewModel::class.java)
        viewModel.currencyLD.observe(this, Observer { data ->
            val resultIntent = Intent()
            resultIntent.putExtra(ADD_RESULT, "OK")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        })
        binding = ActivityAddCurrencyBinding.inflate(layoutInflater)

        fragment = AddCurrencyFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerAddCurrency, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        setContentView(binding.root)
    }

    open fun inject(component: UserComponent): Boolean {
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_add_currency, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send -> {
                fragment.addData()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}