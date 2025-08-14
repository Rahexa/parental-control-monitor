package com.family.safety.helper.models

data class AppUsageData(
    val packageName: String,
    val appName: String,
    val timeUsed: Long, // in milliseconds
    val lastTimeUsed: Long, // timestamp
    val timestamp: Long // when this data was collected
)
