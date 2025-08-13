package com.parentalcontrol.monitor.utils

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings

object DeviceUtils {
    
    fun getDeviceInfo(context: Context): String {
        return """
            Device: ${Build.MANUFACTURER} ${Build.MODEL}
            Android: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})
            Device ID: ${getDeviceId(context)}
        """.trimIndent()
    }
    
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
    
    fun isHuaweiDevice(): Boolean {
        return Build.MANUFACTURER.equals("HUAWEI", true) || 
               Build.MANUFACTURER.equals("HONOR", true)
    }
    
    fun getDeviceName(): String {
        return "${Build.MANUFACTURER} ${Build.MODEL}"
    }
    
    val model: String
        get() = Build.MODEL
    
    fun batteryLevel(context: Context): Int {
        val batteryIntent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        return if (level == -1 || scale == -1) 0 else (level * 100 / scale)
    }
}
