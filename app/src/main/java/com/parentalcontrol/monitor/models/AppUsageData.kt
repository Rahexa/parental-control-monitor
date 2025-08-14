package com.parentalcontrol.monitor.models

data class AppUsageData(
    val id: Long = 0,
    val packageName: String,
    val appName: String,
    val usageTime: Long,
    val timestamp: Long = System.currentTimeMillis()
)
