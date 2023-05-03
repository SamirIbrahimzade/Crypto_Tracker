package com.task.cryptotracker.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.cryptotracker.data.CurrencyHistory

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyHistory: CurrencyHistory)

    @Query("SELECT * FROM currency_history_table")
    fun getHistory(): List<CurrencyHistory>
}