package com.crypto.currencylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.crypto.currencylist.databinding.FragmentCurrencyListBinding

class CurrencyListFragment: Fragment() {

    private var _binding: FragmentCurrencyListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency_list, container, false)
        (_binding as? ViewDataBinding)?.apply {
            lifecycleOwner = viewLifecycleOwner
//            setVariable(BR.vm, viewModel)
        }
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.rvCurrencyList?.layoutManager = LinearLayoutManager(context)
        _binding?.rvCurrencyList?.adapter = CurrencyListAdapter()
    }
}