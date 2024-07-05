package com.crypto.currencylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crypto.currencylist.data.local.CurrencyInfo
import com.crypto.currencylist.databinding.ItemCurrencyBinding

class CurrencyListAdapter(): RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {
    var data: MutableList<CurrencyInfo> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = ItemCurrencyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class CurrencyViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyInfo: CurrencyInfo) {
            binding.tvInitial.text = currencyInfo.name?.first().toString()
            binding.tvCurrencyName.text = currencyInfo.name
            binding.tvCurrencySymbol.text = currencyInfo.symbol
        }
    }
}