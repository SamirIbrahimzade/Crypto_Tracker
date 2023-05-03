package com.task.cryptotracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "currency_history_table")
data class CurrencyHistory(
    @PrimaryKey
    @SerializedName("currency")
    val currency: Currency,
    @SerializedName("date")
    val date: Date
)