package com.task.cryptotracker.currency

import com.task.cryptotracker.data.Currency
import com.task.cryptotracker.data.CurrencyList
import com.task.cryptotracker.data.Response
import com.task.cryptotracker.util.CurrencyUtil.Companion.BITCOIN
import com.task.cryptotracker.util.CurrencyUtil.Companion.BITCOIN_ID
import com.task.cryptotracker.util.CurrencyUtil.Companion.ETHEREUM
import com.task.cryptotracker.util.CurrencyUtil.Companion.ETHEREUM_ID
import com.task.cryptotracker.util.CurrencyUtil.Companion.RIPPLE
import com.task.cryptotracker.util.CurrencyUtil.Companion.RIPPLE_ID
import com.task.cryptotracker.util.CurrencyUtil.Companion.USD_ID
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val currencyAdapter: CurrencyAdapter) {

    suspend fun getCurrencyRates(currencyIds: String, vsCurrencies: String): Response<List<Currency>> =
        try {
            val response = currencyAdapter.getCurrencyRates(currencyIds, vsCurrencies)
            val result = response.body()

            result?.let {
                Response.Success(it.map {
                    return Response.Success(
                        listOf(
                            Currency(BITCOIN, result[BITCOIN_ID]?.get(USD_ID)),
                            Currency(ETHEREUM, result[ETHEREUM_ID]?.get(USD_ID)),
                            Currency(RIPPLE, result[RIPPLE_ID]?.get(USD_ID)),
                        )
                    )
                })
            } ?: Response.Error("result is null")

        } catch (e: Exception) {
            Response.Error(e.toString())
        }
}