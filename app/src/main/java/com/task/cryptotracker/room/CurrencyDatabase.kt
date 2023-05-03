package com.task.cryptotracker.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.cryptotracker.data.CurrencyHistory

@Database(entities = [(CurrencyHistory::class)], version = 1)
@TypeConverters(Converters::class)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}