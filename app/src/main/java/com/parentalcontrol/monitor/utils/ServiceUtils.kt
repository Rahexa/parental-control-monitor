package com.parentalcontrol.monitor.utils

import android.content.Context
import android.content.Intent
import android.app.ActivityManager

object ServiceUtils {
    
    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return activityManager.getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == serviceClass.name }
    }
    
    fun startService(context: Context, serviceClass: Class<*>) {
        try {
            val intent = Intent(context, serviceClass)
            context.startForegroundService(intent)
        } catch (e: Exception) {
            // Handle exception
        }
    }
    
    fun stopService(context: Context, serviceClass: Class<*>) {
        try {
            val intent = Intent(context, serviceClass)
            context.stopService(intent)
        } catch (e: Exception) {
            // Handle exception
        }
    }
}
