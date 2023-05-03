package com.task.cryptotracker.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.cryptotracker.currency.CurrencyRepository
import com.task.cryptotracker.currency.GetCurrencyRatesUseCase
import com.task.cryptotracker.data.Currency
import com.task.cryptotracker.data.Response
import com.task.cryptotracker.util.CurrencyUtil.Companion.CURRENCY_IDS
import com.task.cryptotracker.util.CurrencyUtil.Companion.VS_CURRENCIES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCurrencyRatesUseCase: GetCurrencyRatesUseCase) : ViewModel() {

    private var currencies = listOf<Currency>()

    private val _currencyRatesLiveData = MutableLiveData<List<Currency>>()
    var currencyRatesLiveData: LiveData<List<Currency>> = _currencyRatesLiveData

    fun getCurrency(position: Int): Currency? = _currencyRatesLiveData.value?.get(position)

    fun getCurrencyRates() {
        viewModelScope.launch(Dispatchers.IO){
            when (val response = getCurrencyRatesUseCase.invoke(CURRENCY_IDS, VS_CURRENCIES)) {
                is Response.Success -> response.data?.let {
                    _currencyRatesLiveData.postValue(it)
                }
                else -> {
                    Log.d("Home", response.message.toString())
                }
            }
        }
    }
}