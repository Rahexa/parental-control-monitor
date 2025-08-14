package com.family.safety.helper.models

import com.google.gson.annotations.SerializedName

data class TelegramMessage(
    @SerializedName("chat_id")
    val chatId: String,
    val text: String,
    @SerializedName("parse_mode")
    val parseMode: String = "HTML"
)

data class TelegramResponse(
    val ok: Boolean,
    val description: String? = null
)
