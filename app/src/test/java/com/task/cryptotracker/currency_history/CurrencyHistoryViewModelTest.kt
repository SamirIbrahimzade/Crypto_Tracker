package com.task.cryptotracker.currency_history

import com.task.cryptotracker.data.Currency
import com.task.cryptotracker.data.CurrencyHistory
import com.task.cryptotracker.room.LocalCurrencyRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import java.util.*


internal class CurrencyHistoryViewModelTest {
    private val localCurrencyRepository = mockk<LocalCurrencyRepository>()

    private val currencyHistoryViewModel = CurrencyHistoryViewModel(localCurrencyRepository)

    @Test
    fun getHistory() {
        val list = listOf(
            CurrencyHistory(
                Currency("name1", 1.0),
                Date(1L)
            ),

            CurrencyHistory(
                Currency("name2", 2.0),
                Date(2L)
            )
        )
        every { localCurrencyRepository.getHistory() } returns list

        currencyHistoryViewModel.getHistory()

        verify { localCurrencyRepository.getHistory() }
    }
}