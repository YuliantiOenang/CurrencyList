package com.crypto.currencylist

import com.crypto.currencylist.data.CurrencyInfo
import com.crypto.currencylist.data.CurrencyInfoDao
import com.crypto.currencylist.repository.CurrencyInfoRepository
import com.crypto.currencylist.repository.LocalDataStorage
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith


class CurrencyRepositoryUnitTest {
    private var currencyInfoRepository: CurrencyInfoRepository? = null
    private var mockCurrencyInfoDao: CurrencyInfoDao? = null

    @Before
    fun setup() {
        mockCurrencyInfoDao = mockk<CurrencyInfoDao>()
        currencyInfoRepository = LocalDataStorage(mockCurrencyInfoDao!!)
    }

    @Test
    fun `test getAllCurrencyList returns all currency lists`() = runTest {
        val infos = listOf(
            CurrencyInfo(1, "US Dollar", "$", "USD"),
            CurrencyInfo(2, "Etherium", "ETH")
        )
        coEvery { mockCurrencyInfoDao?.all } returns infos

        val result = currencyInfoRepository?.getAllCurrencyLists()

        assertEquals(infos, result)
        verify { mockCurrencyInfoDao?.all }
    }

    @Test
    fun `test getAllCurrencyList null returns null`() = runTest {
        coEvery { mockCurrencyInfoDao?.all } returns null

        val result = currencyInfoRepository?.getAllCurrencyLists()

        // Assert
        assertNull("Should return null when currency list not found", result)
    }

    @Test
    fun `getAllCurrencyList throws an exception when database access fails`() = runTest {
        // Setup MockK to throw an exception when accessing the database
        coEvery { mockCurrencyInfoDao?.all } throws RuntimeException("Database access error")

        // Execute and assert that the exception is thrown when the method is called
        assertFailsWith<RuntimeException>("Should throw an exception when database access fails") {
            currencyInfoRepository?.getAllCurrencyLists()
        }
    }

    @Test
    fun `test getFiatCurrencyList returns fiat currency list`() = runTest {
        val infos = listOf(CurrencyInfo(1, "US Dollar", "$", "USD"))
        coEvery { mockCurrencyInfoDao?.loadAllFiat() } returns infos

        val result = currencyInfoRepository?.getAllFiatCurrencyLists()

        assertEquals(infos, result)
    }

    @Test
    fun `test getCryptoCurrencyList returns fiat currency list`() = runTest {
        val infos = listOf(CurrencyInfo(1, "Etherium", "ETH"))
        coEvery { mockCurrencyInfoDao?.loadAllCrypto() } returns infos

        val result = currencyInfoRepository?.getAllCryptoCurrencyLists()

        assertEquals(infos, result)
        verify { mockCurrencyInfoDao?.loadAllCrypto() }
    }

    @Test
    fun `test insert new data will call insert all from dao`() = runTest {
        coEvery { mockCurrencyInfoDao?.insertAll(CurrencyInfo()) } returns Unit
        currencyInfoRepository?.insert(CurrencyInfo())
        verify { mockCurrencyInfoDao?.insertAll(CurrencyInfo()) }
    }

    @Test
    fun `test clear all will call delete from dao`() = runTest {
        coEvery { mockCurrencyInfoDao?.delete() } returns Unit
        currencyInfoRepository?.deleteAllCurrencylist()

        verify { mockCurrencyInfoDao?.delete() }
    }

    @Test
    fun `search query will call find by name from dao`() = runTest {
        coEvery { mockCurrencyInfoDao?.findByName("") } returns listOf(
            CurrencyInfo(
                1,
                "US Dollar",
                "$",
                "USD"
            )
        )
        currencyInfoRepository?.searchQuery("")

        verify { mockCurrencyInfoDao?.findByName("") }
    }
}