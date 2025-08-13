package com.parentalcontrol.monitor.api

import com.parentalcontrol.monitor.models.TelegramMessage
import com.parentalcontrol.monitor.models.TelegramResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TelegramApi {
    
    @POST("sendMessage")
    suspend fun sendMessage(@Body message: TelegramMessage): TelegramResponse
}
