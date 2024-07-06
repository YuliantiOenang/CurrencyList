package com.crypto.currencylist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.crypto.currencylist.AddCurrencyActivity.companion.ADD_RESULT
import com.crypto.currencylist.di.UserComponent
import com.crypto.currencylist.databinding.ActivityAddCurrencyBinding

class AddCurrencyActivity: AppCompatActivity(), AddCurrencyFragment.ActivityFinisher {
    object companion {
        const val ADD_RESULT = "ADD_RESULT"
    }
    private lateinit var _binding: ActivityAddCurrencyBinding
    private lateinit var _fragment: AddCurrencyFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userComponent = (this.applicationContext as CurrencyListApplication).userComponent
        if (!inject(userComponent)) {
            userComponent.inject(this)
        }
        _binding = ActivityAddCurrencyBinding.inflate(layoutInflater)

        _fragment = AddCurrencyFragment().apply {
            activityFinisher = this@AddCurrencyActivity
        }
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerAddCurrency, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        setContentView(_binding.root)
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
                _fragment.addData()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finishActivity() {
        val resultIntent = Intent()
        resultIntent.putExtra(ADD_RESULT, "OK")
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}