package com.crypto.currencylist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CurrencyInfoDao {
    @get:Query("SELECT * FROM currencyinfo")
    val all: List<CurrencyInfo>?

    @Query("SELECT * FROM currencyinfo WHERE code is NULL")
    fun loadAllCrypto(): List<CurrencyInfo?>?

    @Query("SELECT * FROM currencyinfo WHERE code is not NULL")
    fun loadAllFiat(): List<CurrencyInfo?>?

    @Query("SELECT * FROM currencyinfo WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String?): CurrencyInfo?

    @Insert
    fun insertAll(vararg currencyInfos: CurrencyInfo?)

    @Query("DELETE FROM currencyinfo")
    fun delete()
}
