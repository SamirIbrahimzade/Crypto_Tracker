package com.task.cryptotracker.util

import com.task.cryptotracker.room.CurrencyDatabase

class CurrencyUtil {
    companion object {
        const val ETHEREUM_ID = "ethereum"
        const val BITCOIN_ID = "bitcoin"
        const val RIPPLE_ID = "ripple"
        const val USD_ID = "usd"

        const val CURRENCY_IDS = "${BITCOIN_ID},${ETHEREUM_ID},${RIPPLE_ID}"
        const val VS_CURRENCIES = USD_ID

        const val MIN_RANGE = "min_range"
        const val MAX_RANGE = "max_range"

        const val BITCOIN = "Bitcoin"
        const val ETHEREUM = "Ethereum"
        const val RIPPLE = "Ripple"

        const val DATABASE_NAME = "currency_database"
    }
}