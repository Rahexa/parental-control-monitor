package com.parentalcontrol.monitor.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

class HuaweiOptimizationHelper(private val context: Context) {
    
    companion object {
        private const val HUAWEI_MANUFACTURER = "HUAWEI"
        private const val HONOR_MANUFACTURER = "HONOR"
    }
    
    fun isHuaweiDevice(): Boolean {
        return Build.MANUFACTURER.equals(HUAWEI_MANUFACTURER, ignoreCase = true) ||
               Build.MANUFACTURER.equals(HONOR_MANUFACTURER, ignoreCase = true)
    }
    
    fun requestHuaweiOptimizations() {
        if (!isHuaweiDevice()) return
        
        showHuaweiOptimizationDialog()
    }
    
    private fun showHuaweiOptimizationDialog() {
        AlertDialog.Builder(context)
            .setTitle("Huawei Device Optimization Required")
            .setMessage("""
                For reliable monitoring on Huawei devices, please complete these steps:
                
                1. Disable Battery Optimization
                2. Enable Auto-start
                3. Lock app in Recent Apps
                4. Disable App Launch Management
                
                Would you like to open the settings now?
            """.trimIndent())
            .setPositiveButton("Open Settings") { _, _ ->
                openHuaweiPowerSettings()
            }
            .setNegativeButton("Manual Setup") { _, _ ->
                showManualInstructions()
            }
            .setCancelable(false)
            .show()
    }
    
    private fun openHuaweiPowerSettings() {
        try {
            // Try to open Huawei's protected apps settings
            val intent = Intent().apply {
                component = ComponentName(
                    "com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
                )
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            
            if (isIntentAvailable(intent)) {
                context.startActivity(intent)
                return
            }
            
            // Fallback to battery optimization settings
            openBatteryOptimizationSettings()
            
        } catch (e: Exception) {
            openBatteryOptimizationSettings()
        }
    }
    
    private fun openBatteryOptimizationSettings() {
        try {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                data = Uri.parse("package:${context.packageName}")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback to general battery settings
            val intent = Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }
    
    private fun showManualInstructions() {
        AlertDialog.Builder(context)
            .setTitle("Manual Setup Instructions")
            .setMessage("""
                Huawei Nova 3i Manual Setup:
                
                1. BATTERY OPTIMIZATION:
                   Settings → Apps → Parental Control Monitor → Battery → Ignore battery optimization
                
                2. AUTO-START:
                   Phone Manager → Auto-start → Enable this app
                
                3. RECENT APPS LOCK:
                   Open Recent Apps → Find this app → Pull down → Lock
                
                4. APP LAUNCH:
                   Settings → Apps → Parental Control Monitor → App launch → Manage manually → Enable all
                
                5. NOTIFICATIONS:
                   Settings → Apps → Parental Control Monitor → Notifications → Allow all
                
                6. PROTECTED APPS:
                   Phone Manager → Protected apps → Enable this app
            """.trimIndent())
            .setPositiveButton("Got it") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun isIntentAvailable(intent: Intent): Boolean {
        return context.packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        ).isNotEmpty()
    }
    
    fun checkHuaweiOptimizations(): List<String> {
        val issues = mutableListOf<String>()
        
        if (!isHuaweiDevice()) return issues
        
        // Check if app is in battery optimization whitelist
        if (!isBatteryOptimizationIgnored()) {
            issues.add("Battery optimization not disabled")
        }
        
        // Add more checks as needed
        return issues
    }
    
    private fun isBatteryOptimizationIgnored(): Boolean {
        return try {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as android.os.PowerManager
            powerManager.isIgnoringBatteryOptimizations(context.packageName)
        } catch (e: Exception) {
            false
        }
    }
    
    fun getHuaweiSpecificInstructions(): String {
        return """
        HUAWEI NOVA 3i SPECIFIC SETUP:
        
        1. Phone Manager App:
           • Open Phone Manager
           • Go to "Protected apps"
           • Find "Parental Control Monitor"
           • Enable protection
        
        2. Auto-start Management:
           • Phone Manager → Auto-start
           • Enable for this app
        
        3. App Launch Management:
           • Settings → Apps & notifications
           • Find app → App launch
           • Disable "Manage automatically"
           • Enable all three options manually
        
        4. Lock in Recent Apps:
           • Open recent apps (square button)
           • Find this app
           • Pull down on the app card
           • Tap the lock icon
        
        5. Notification Permissions:
           • Settings → Apps & notifications
           • Special access → Notification access
           • Enable for this app
        
        6. EMUI Battery Settings:
           • Settings → Battery
           • App launch → Find app
           • Set to "Manual management"
           • Enable all options
        """.trimIndent()
    }
}
