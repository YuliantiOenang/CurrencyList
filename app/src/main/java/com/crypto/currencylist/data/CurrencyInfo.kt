package com.crypto.currencylist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    var name: String? = null,
    var symbol: String? = null,
    var code: String? = null
)