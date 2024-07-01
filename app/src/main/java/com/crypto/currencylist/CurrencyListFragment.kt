package com.crypto.currencylist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.crypto.currencylist.DI.AppComponent
import com.crypto.currencylist.DI.CurrencyViewModelFactory
import com.crypto.currencylist.data.CurrencyInfo
import com.crypto.currencylist.databinding.FragmentCurrencyListBinding
import java.util.Currency
import javax.inject.Inject

class CurrencyListFragment: Fragment() {

    private var _binding: FragmentCurrencyListBinding? = null
    private var viewModel: CurrencyListViewModel? = null
    private var _adapter: CurrencyListAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val userComponent = (context.applicationContext as CurrencyListApplication).userComponent
        userComponent.inject(this)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Initialize the ViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyListViewModel::class.java]
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency_list, container, false)
        (_binding as? ViewDataBinding)?.apply {
            lifecycleOwner = viewLifecycleOwner
//            setVariable(BR.vm, viewModel)
        }
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyListViewModel::class.java]
        _binding?.rvCurrencyList?.layoutManager = LinearLayoutManager(context)
        _binding?.rvCurrencyList?.adapter = getAdapter()
        viewModel?.currencyList?.observe(viewLifecycleOwner) {
            if (it != null)
            {
                getAdapter().data = it.toMutableList()
            }
        }


    }

    private fun getAdapter() : CurrencyListAdapter {
        if (_adapter == null) {
            _adapter = CurrencyListAdapter()
        }
        return _adapter!!
    }

    fun addItem() {
        val newCI = CurrencyInfo(name = "Rupiah", symbol = "IDR", code = "ID")
        viewModel?.insert(newCI)
    }

    fun clearList() {
        viewModel?.deleteAllCurrencylist()
    }

    fun toCrypto() {
        viewModel?.getAllCryptoCurrencyLists()
    }

    fun toFiat() {
        viewModel?.getAllFiatCurrencyLists()
    }

    fun showAll() {
        viewModel?.getAllCurrencyLists()
    }
}