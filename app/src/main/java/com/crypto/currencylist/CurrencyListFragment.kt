package com.crypto.currencylist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.crypto.currencylist.databinding.FragmentCurrencyListBinding
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyListViewModel::class.java]
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency_list, container, false)
        (_binding as? ViewDataBinding)?.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.vm, viewModel)
        }
        _binding?.etSearch?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                _binding?.etSearch?.hint = ""
                _binding?.ivBack?.visibility = View.VISIBLE
                _binding?.ivSearch?.setImageResource(R.drawable.ic_close)
                viewModel?.state = CurrencyListViewModel.State.SEARCHING
                showKeyboardFrom(requireContext(), _binding?.etSearch)
            } else {
                _binding?.etSearch?.hint = resources.getString(R.string.search_hint)
                _binding?.ivBack?.visibility = View.GONE
                _binding?.ivSearch?.setImageResource(R.drawable.ic_search)
                viewModel?.state = CurrencyListViewModel.State.IDLE
            }
        }

        _binding?.etSearch?.setOnClickListener { view ->
            _binding?.etSearch?.hint = ""
            _binding?.ivBack?.visibility = View.VISIBLE
            _binding?.ivSearch?.setImageResource(R.drawable.ic_close)
            viewModel?.state = CurrencyListViewModel.State.SEARCHING
            showKeyboardFrom(requireContext(), _binding?.etSearch)
        }

        _binding?.ivBack?.setOnClickListener {
            backToNormalView()
        }

        _binding?.ivSearch?.setOnClickListener {
            if (viewModel?.state == CurrencyListViewModel.State.SEARCHING) {
                backToNormalView()
            } else {
                _binding?.etSearch?.requestFocus()
            }
        }
        _binding?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.toString()?.let {
                    if (it.isNotEmpty()) {
                        viewModel?.searchName(it)
                    } else {
                        viewModel?.getAllCurrencyLists()
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
        return requireNotNull(_binding).root
    }

    private fun backToNormalView() {
        _binding?.etSearch?.setText("")
        _binding?.etSearch?.hint = resources.getString(R.string.search_hint)
        _binding?.ivBack?.visibility = View.GONE
        _binding?.ivSearch?.setImageResource(R.drawable.ic_search)
        _binding?.etSearch?.clearFocus()
        hideKeyboardFrom(requireContext(), _binding?.etSearch)
        viewModel?.state = CurrencyListViewModel.State.IDLE
        viewModel?.getAllCurrencyLists()
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

    private fun hideKeyboardFrom(context: Context, view: View?) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showKeyboardFrom(context: Context, view: View?) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            imm.showSoftInput(view, 0)
        }
    }
}