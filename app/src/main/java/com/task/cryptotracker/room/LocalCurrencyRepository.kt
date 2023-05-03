package com.task.cryptotracker.room

import com.task.cryptotracker.data.CurrencyHistory
import javax.inject.Inject

class LocalCurrencyRepository @Inject constructor(private val currencyDao: CurrencyDao) {

    fun saveCurrencyRates(currencyHistory: CurrencyHistory) {
        currencyDao.insert(currencyHistory)
    }

    fun getHistory() = currencyDao.getHistory()

}