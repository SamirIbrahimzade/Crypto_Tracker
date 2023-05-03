package com.task.cryptotracker.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.cryptotracker.util.CurrencyUtil.Companion.MAX_RANGE
import com.task.cryptotracker.util.CurrencyUtil.Companion.MIN_RANGE
import com.task.cryptotracker.util.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository: DataStoreRepository) : ViewModel() {

    private val _minRangeLiveData = MutableLiveData<String>()
    var minRateLiveData: LiveData<String> = _minRangeLiveData

    private val _maxRateLiveData = MutableLiveData<String>()
    var maxRateLiveData: LiveData<String> = _maxRateLiveData

    fun saveRates(currencyName: String, minValue: String, maxValue: String) {
        viewModelScope.launch {
            saveRatesToDataStore(currencyName, minValue, maxValue)
        }
    }

    private suspend fun saveRatesToDataStore(currencyName: String, minValue: String, maxValue: String) {
        if (minValue.isNotEmpty()) {
            repository.putString("$MIN_RANGE$currencyName", minValue)
        }
        if (maxValue.isNotEmpty()) {
            repository.putString("$MAX_RANGE$currencyName", maxValue)
        }
    }

    fun getMaxRate(currencyName: String) {
        viewModelScope.launch {
            getMaxRateFromDataStore(currencyName)
        }
    }

    fun getMinRate(currencyName: String) {
        viewModelScope.launch {
            getMinRateFromDataStore(currencyName)
        }
    }

    private suspend fun getMaxRateFromDataStore(currencyName: String) = viewModelScope.launch {
        _maxRateLiveData.postValue(repository.getString("$MAX_RANGE$currencyName"))
    }

    private suspend fun getMinRateFromDataStore(currencyName: String) = viewModelScope.launch {
        _minRangeLiveData.postValue(repository.getString("$MIN_RANGE$currencyName"))
    }

}