package com.parentalcontrol.monitor.models

data class FileAccessData(
    val fileName: String,
    val filePath: String,
    val accessType: String,
    val accessTime: Long = System.currentTimeMillis(), // For compatibility
    val fileSize: Long = 0L, // For compatibility
    val fileType: String = "unknown", // For compatibility
    val timestamp: Long
)
