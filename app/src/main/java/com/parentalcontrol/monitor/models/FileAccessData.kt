package com.parentalcontrol.monitor.models

data class FileAccessData(
    val fileName: String,
    val filePath: String,
    val accessType: String,
    val timestamp: Long
)
