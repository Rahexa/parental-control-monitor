package com.parentalcontrol.monitor.models

data class FileAccessData(
    val id: Long = 0,
    val filePath: String,
    val fileName: String,
    val accessType: String,
    val fileSize: Long = 0L,
    val fileType: String = "",
    val accessTime: Long = System.currentTimeMillis(),
    val timestamp: Long = System.currentTimeMillis()
)
