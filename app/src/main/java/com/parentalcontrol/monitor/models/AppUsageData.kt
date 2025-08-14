package com.parentalcontrol.monitor.models

data class AppUsageData(
    val packageName: String,
    val appName: String,
    val usageTime: Long,
    val timestamp: Long
)
