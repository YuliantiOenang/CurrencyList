package com.crypto.currencylist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyInfo(
    @PrimaryKey
    var id: String,

    var name: String? = null,
    var symbol: String? = null,
    var code: String? = null
)