package com.task.cryptotracker.currency

import com.task.cryptotracker.data.Currency
import com.task.cryptotracker.data.Response
import javax.inject.Inject

class GetCurrencyRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(
        currencyIds: String,
        vsCurrencies: String
    ): Response<List<Currency>> {
        return repository.getCurrencyRates(currencyIds, vsCurrencies)
    }
}