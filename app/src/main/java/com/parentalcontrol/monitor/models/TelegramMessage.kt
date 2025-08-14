package com.parentalcontrol.monitor.models

data class TelegramMessage(
    val id: Long = 0,
    val chatId: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val sent: Boolean = false
)
