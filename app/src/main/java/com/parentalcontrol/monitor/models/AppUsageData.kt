package com.parentalcontrol.monitor.models

data class AppUsageData(
    val packageName: String,
    val appName: String,
    val usageTime: Long,
    val timeUsed: Long = usageTime, // Alias for compatibility
    val lastTimeUsed: Long = usageTime, // Alias for compatibility
    val timestamp: Long
)
