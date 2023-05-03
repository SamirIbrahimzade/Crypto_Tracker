package com.task.cryptotracker.currency_history

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.text.isDigitsOnly
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.task.cryptotracker.R
import com.task.cryptotracker.currency.GetCurrencyRatesUseCase
import com.task.cryptotracker.data.CurrencyHistory
import com.task.cryptotracker.data.Response
import com.task.cryptotracker.room.LocalCurrencyRepository
import com.task.cryptotracker.util.CurrencyUtil
import com.task.cryptotracker.util.CurrencyUtil.Companion.MAX_RANGE
import com.task.cryptotracker.util.CurrencyUtil.Companion.MIN_RANGE
import com.task.cryptotracker.util.DataStoreRepository
import com.task.cryptotracker.util.NotificationManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
@HiltWorker
class ApiWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
    val localCurrencyRepository: LocalCurrencyRepository,
    val dataStoreRepository: DataStoreRepository,
    val notificationManager: NotificationManager,
    val getCurrencyRatesUseCase: GetCurrencyRatesUseCase
) : Worker(context, workerParams) {


    override fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            val response = getCurrencyRatesUseCase.invoke(CurrencyUtil.CURRENCY_IDS, CurrencyUtil.VS_CURRENCIES)
            if (response is Response.Success) {
                response.data?.forEach {
                    localCurrencyRepository.saveCurrencyRates(CurrencyHistory(it, Date()))
                }
                checkRanges(response.data)
            }
        }

        return Result.success()
    }

    private fun checkRanges(currencyList: List<com.task.cryptotracker.data.Currency>?) {
        dataStoreRepository.apply {
            CoroutineScope(Dispatchers.IO).launch {
                currencyList?.forEach {
                    val minRange = getString("$MIN_RANGE${it.name}").toString()
                    val maxRange = getString("$MAX_RANGE${it.name}").toString()
                    it.amount?.let { amount ->
                        if (minRange.isDigitsOnly() && amount < minRange.toDouble()) it.name?.let { name ->
                            showLessNotification(name)
                        }
                        if (maxRange.isDigitsOnly() && amount > maxRange.toDouble()) it.name?.let { name ->
                            showMoreNotification(name)
                        }
                    }
                }
            }
        }
    }

    private fun showLessNotification(cryptoName: String) {
        notificationManager.sendDefaultNotification(
            context.getString(R.string.push_title),
            String.format(context.getString(R.string.push_less_message), cryptoName)
        )
    }

    private fun showMoreNotification(cryptoName: String) {
        notificationManager.sendDefaultNotification(
            context.getString(R.string.push_title),
            String.format(context.getString(R.string.push_more_message), cryptoName)
        )
    }

    companion object {
        fun startRequests(context: Context) {
            val periodicWorkRequest = PeriodicWorkRequest.Builder(ApiWorker::class.java, 15, TimeUnit.MINUTES)
                .build()
            WorkManager.getInstance(context).enqueue(periodicWorkRequest)

            val work = OneTimeWorkRequest.Builder(ApiWorker::class.java).build()
            WorkManager.getInstance(context).enqueue(work)
        }
    }
}