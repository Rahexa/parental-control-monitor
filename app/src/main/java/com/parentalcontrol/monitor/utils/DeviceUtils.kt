package com.parentalcontrol.monitor.utils

import android.content.Context
import android.os.Build

object DeviceUtils {
    
    fun getDeviceInfo(context: Context): String {
        return "${Build.MANUFACTURER} ${Build.MODEL} (${Build.VERSION.RELEASE})"
    }
    
    fun getDeviceInfo(): String {
        return "${Build.MANUFACTURER} ${Build.MODEL} (${Build.VERSION.RELEASE})"
    }
    
    val model: String
        get() = "${Build.MANUFACTURER} ${Build.MODEL}"
    
    val batteryLevel: Int
        get() = 100 // Simplified implementation
    
    fun isHuaweiDevice(): Boolean {
        return Build.MANUFACTURER.equals("HUAWEI", ignoreCase = true) ||
               Build.MANUFACTURER.equals("HONOR", ignoreCase = true)
    }
    
    fun getDeviceId(): String {
        return Build.ID ?: "unknown"
    }
    
    fun isDeviceRooted(): Boolean {
        return false // Simplified implementation
    }
}
