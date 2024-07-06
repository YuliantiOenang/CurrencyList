package com.crypto.currencylist.feature.currencylist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.crypto.currencylist.CurrencyListApplication
import com.crypto.currencylist.feature.addcurrency.AddCurrencyActivity
import com.crypto.currencylist.feature.addcurrency.AddCurrencyActivity.companion.ADD_RESULT
import com.crypto.currencylist.R
import com.crypto.currencylist.di.UserComponent
import com.crypto.currencylist.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity(), CurrencyListFragment.ToggleVisibilityButton {
    private lateinit var _binding: ActivityDemoBinding
    private lateinit var _resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userComponent = (this.applicationContext as CurrencyListApplication).userComponent
        if (!inject(userComponent)) {
            userComponent.inject(this)
        }
        _binding = ActivityDemoBinding.inflate(layoutInflater)

        val fragment = CurrencyListFragment().apply {
            hideButton = this@DemoActivity
        }
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        _resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data?.getStringExtra(ADD_RESULT) == "OK") {
                    (fragment as? CurrencyListFragment)?.showAll()
                }
            }
        }

        _binding.btnAddInstrument.setOnClickListener {
            val intent = Intent(this, AddCurrencyActivity::class.java)
            _resultLauncher.launch(intent)
        }

        _binding.btnToCrypto.setOnClickListener {
            (fragment as? CurrencyListFragment)?.toCrypto()
        }

        _binding.toFiat.setOnClickListener {
            (fragment as? CurrencyListFragment)?.toFiat()
        }

        _binding.btnShowAll.setOnClickListener {
            (fragment as? CurrencyListFragment)?.showAll()
        }

        _binding.btnClearList.setOnClickListener {
            (fragment as? CurrencyListFragment)?.clearList()
        }
        setContentView(_binding.root)
    }

    override fun showButton() {
        _binding.btnClearList.visibility = View.VISIBLE
        _binding.btnAddInstrument.visibility = View.VISIBLE
        _binding.btnToCrypto.visibility = View.VISIBLE
        _binding.toFiat.visibility = View.VISIBLE
        _binding.btnShowAll.visibility = View.VISIBLE
    }

    override fun hideButton() {
        _binding.btnClearList.visibility = View.GONE
        _binding.btnAddInstrument.visibility = View.GONE
        _binding.btnToCrypto.visibility = View.GONE
        _binding.toFiat.visibility = View.GONE
        _binding.btnShowAll.visibility = View.GONE
    }

    open fun inject(component: UserComponent): Boolean {
        return false
    }
}