package com.crypto.currencylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crypto.currencylist.databinding.ItemCurrencyBinding

class CurrencyListAdapter: RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = ItemCurrencyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 100
    }

    class CurrencyViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tvCurrencyName.text = "Bitcoin"
            binding.tvCurrencySymbol.text = "BTC"
        }
    }
}