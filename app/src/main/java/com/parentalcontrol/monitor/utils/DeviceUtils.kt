package com.parentalcontrol.monitor.utils

import android.content.Context
import android.os.Build
import com.parentalcontrol.monitor.models.DeviceInfo

object DeviceUtils {
    
    fun getDeviceInfo(context: Context): DeviceInfo {
        return DeviceInfo(
            model = "${Build.MANUFACTURER} ${Build.MODEL}",
            batteryLevel = 100, // Simplified implementation
            manufacturer = Build.MANUFACTURER,
            version = Build.VERSION.RELEASE
        )
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
