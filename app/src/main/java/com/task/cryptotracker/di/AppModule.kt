package com.task.cryptotracker.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import androidx.work.WorkerParameters
import com.task.cryptotracker.App
import com.task.cryptotracker.currency.CurrencyAdapter
import com.task.cryptotracker.currency.CurrencyRepository
import com.task.cryptotracker.currency_history.ApiWorker
import com.task.cryptotracker.room.CurrencyDatabase
import com.task.cryptotracker.util.CurrencyUtil.Companion.DATABASE_NAME
import com.task.cryptotracker.util.DataStoreRepository
import com.task.cryptotracker.util.NotificationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.coingecko.com/api/v3/simple/"

    @Singleton
    @Provides
    fun provideCurrencyAdapter(): CurrencyAdapter = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyAdapter::class.java)

    @Singleton
    @Provides
    fun provideCurrencyRepository(api: CurrencyAdapter): CurrencyRepository = CurrencyRepository(api)

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext app: Context
    ): DataStoreRepository = DataStoreRepository(app)

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext app: Context
    ): NotificationManager = NotificationManager(app)

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(app, CurrencyDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideCurrencyDao(
        database: CurrencyDatabase
    ) = database.currencyDao()

}