package com.parentalcontrol.monitor.models

data class TelegramMessage(
    val chatId: String,
    val text: String,
    val parseMode: String = "HTML"
)
