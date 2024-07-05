package com.crypto.currencylist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.crypto.currencylist.AddCurrencyActivity.companion.ADD_RESULT
import com.crypto.currencylist.di.UserComponent
import com.crypto.currencylist.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity(), CurrencyListFragment.ToggleVisibilityButton {
    private lateinit var binding: ActivityDemoBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userComponent = (this.applicationContext as CurrencyListApplication).userComponent
        if (!inject(userComponent)) {
            userComponent.inject(this)
        }
        binding = ActivityDemoBinding.inflate(layoutInflater)

        val fragment = CurrencyListFragment().apply {
            hideButton = this@DemoActivity
        }
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data?.getStringExtra(ADD_RESULT) == "OK") {
                    (fragment as? CurrencyListFragment)?.showAll()
                }
            }
        }

        binding.btnAddInstrument.setOnClickListener {
            val intent = Intent(this, AddCurrencyActivity::class.java)
            resultLauncher.launch(intent)
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

    override fun showButton() {
        binding.btnClearList.visibility = View.VISIBLE
        binding.btnAddInstrument.visibility = View.VISIBLE
        binding.btnToCrypto.visibility = View.VISIBLE
        binding.toFiat.visibility = View.VISIBLE
        binding.btnShowAll.visibility = View.VISIBLE
    }

    override fun hideButton() {
        binding.btnClearList.visibility = View.GONE
        binding.btnAddInstrument.visibility = View.GONE
        binding.btnToCrypto.visibility = View.GONE
        binding.toFiat.visibility = View.GONE
        binding.btnShowAll.visibility = View.GONE
    }

    open fun inject(component: UserComponent): Boolean {
        return false
    }
}