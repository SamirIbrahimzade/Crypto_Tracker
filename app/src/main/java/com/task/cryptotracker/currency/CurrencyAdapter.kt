package com.task.cryptotracker.currency

import com.task.cryptotracker.data.CurrencyList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAdapter {

    @GET("price")
    suspend fun getCurrencyRates(
        @Query("ids") currencyIds: String,
        @Query("vs_currencies") vsCurrencies: String
    ): Response<CurrencyList>
}