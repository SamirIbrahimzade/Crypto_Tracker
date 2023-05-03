package com.task.cryptotracker.currency_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.cryptotracker.data.CurrencyHistory
import com.task.cryptotracker.room.LocalCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyHistoryViewModel @Inject constructor(private val localCurrencyRepository: LocalCurrencyRepository) :
    ViewModel() {

    private val _historyLiveData = MutableLiveData<List<CurrencyHistory>>()
    var historyLiveData: LiveData<List<CurrencyHistory>> = _historyLiveData


    fun getHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val history = localCurrencyRepository.getHistory()
            _historyLiveData.postValue(history)
        }
    }
}