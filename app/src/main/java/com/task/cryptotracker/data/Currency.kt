package com.task.cryptotracker.data

import androidx.room.Entity

@Entity
data class Currency(val name: String?, val amount: Double?)