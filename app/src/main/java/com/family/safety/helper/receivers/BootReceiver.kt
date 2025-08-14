package com.family.safety.helper.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.family.safety.helper.services.MonitoringService

class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED,
            Intent.ACTION_PACKAGE_REPLACED -> {
                startMonitoringService(context)
            }
        }
    }
    
    private fun startMonitoringService(context: Context) {
        val sharedPrefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val isMonitoringEnabled = sharedPrefs.getBoolean("monitoring_enabled", false)
        
        if (isMonitoringEnabled) {
            val serviceIntent = Intent(context, MonitoringService::class.java)
            ContextCompat.startForegroundService(context, serviceIntent)
        }
    }
}
