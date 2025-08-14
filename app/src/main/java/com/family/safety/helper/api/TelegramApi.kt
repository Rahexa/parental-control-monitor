package com.family.safety.helper.api

import com.family.safety.helper.models.TelegramMessage
import com.family.safety.helper.models.TelegramResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TelegramApi {
    
    @POST("sendMessage")
    suspend fun sendMessage(@Body message: TelegramMessage): TelegramResponse
}
