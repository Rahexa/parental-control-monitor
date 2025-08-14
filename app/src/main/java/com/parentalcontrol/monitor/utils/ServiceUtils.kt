package com.parentalcontrol.monitor.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent

object ServiceUtils {
    
    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
    
    fun startService(context: Context, serviceClass: Class<*>) {
        val intent = Intent(context, serviceClass)
        context.startForegroundService(intent)
    }
    
    fun stopService(context: Context, serviceClass: Class<*>) {
        val intent = Intent(context, serviceClass)
        context.stopService(intent)
    }
}
