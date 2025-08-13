package com.parentalcontrol.monitor.models

data class FileAccessData(
    val fileName: String,
    val filePath: String,
    val fileSize: Long,
    val fileType: String,
    val accessTime: Long,
    val accessType: String // "read", "write", "delete", etc.
)
