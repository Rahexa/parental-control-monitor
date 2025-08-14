package com.parentalcontrol.monitor.api

import retrofit2.http.GET
import retrofit2.http.Query

interface TelegramApi {
    
    @GET("sendMessage")
    suspend fun sendMessage(
        @Query("chat_id") chatId: String,
        @Query("text") text: String
    ): TelegramResponse
}

data class TelegramResponse(
    val ok: Boolean,
    val result: MessageResult?
)

data class MessageResult(
    val message_id: Int,
    val date: Long,
    val text: String?
)
