package com.parentalcontrol.monitor.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TelegramApi {
    
    @GET("sendMessage")
    suspend fun sendMessage(
        @Query("chat_id") chatId: String,
        @Query("text") text: String
    ): Response<Any>
}
