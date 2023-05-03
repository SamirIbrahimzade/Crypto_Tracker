package com.task.cryptotracker.room

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromCurrency(currency: com.task.cryptotracker.data.Currency): String {
        return currency.name + "-" + currency.amount
    }

    @TypeConverter
    fun toCurrency(resString: String): com.task.cryptotracker.data.Currency {
        return com.task.cryptotracker.data.Currency(
            resString.substringBefore("-"),
            resString.substringAfter("-").toDouble()
        )
    }
}