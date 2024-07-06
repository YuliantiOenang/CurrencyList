package com.crypto.currencylist.feature.currencylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.crypto.currencylist.data.local.CurrencyInfo
import com.crypto.currencylist.databinding.ItemCurrencyBinding

class CurrencyListAdapter: ListAdapter<CurrencyInfo, CurrencyListAdapter.CurrencyViewHolder>(
    CurrencyInfoDiffCallback
) {
    var data: MutableList<CurrencyInfo> = mutableListOf()

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
        fun bind(currencyInfo: CurrencyInfo?) {
            binding.tvInitial.text = currencyInfo?.name?.firstOrNull().toString()
            binding.tvCurrencyName.text = currencyInfo?.name
            binding.tvCurrencySymbol.text = currencyInfo?.symbol
        }
    }

    object CurrencyInfoDiffCallback : DiffUtil.ItemCallback<CurrencyInfo>() {
        override fun areItemsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
            return oldItem == newItem
        }
    }
}