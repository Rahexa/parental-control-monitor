package com.parentalcontrol.monitor.utils

import android.content.Context
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
}
