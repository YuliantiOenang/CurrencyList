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
import com.crypto.currencylist.data.local.CurrencyInfo
import com.crypto.currencylist.databinding.FragmentAddCurrencyBinding
import javax.inject.Inject

class AddCurrencyFragment : Fragment() {
    private var _binding: FragmentAddCurrencyBinding? = null
    private var viewModel: AddCurrencyViewModel? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[AddCurrencyViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val userComponent = (context.applicationContext as CurrencyListApplication).userComponent
        userComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_currency, container, false)
        (_binding as? ViewDataBinding)?.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.vm, viewModel)
        }
        return requireNotNull(_binding).root
    }

    fun addData() {
        if (_binding?.etCode?.text?.isNotBlank() == true) {
            viewModel?.addCurrency(
                CurrencyInfo(
                    id = _binding?.etId?.text.toString(),
                    name = _binding?.etName?.text.toString(),
                    symbol = _binding?.etSymbol?.text.toString(),
                    code = _binding?.etCode?.text.toString()
                )
            )
        } else {
            viewModel?.addCurrency(
                CurrencyInfo(
                    id = _binding?.etId?.text.toString(),
                    name = _binding?.etName?.text.toString(),
                    symbol = _binding?.etSymbol?.text.toString()
                )
            )
        }
    }
}