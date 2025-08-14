package com.parentalcontrol.monitor.database

import androidx.room.*
import com.parentalcontrol.monitor.models.AppUsageData
import com.parentalcontrol.monitor.models.FileAccessData
import com.parentalcontrol.monitor.models.TelegramMessage

@Entity(tableName = "app_usage")
data class AppUsageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val appName: String,
    val usageTime: Long,
    val lastTimeUsed: Long,
    val timestamp: Long
)

@Entity(tableName = "file_access")  
data class FileAccessEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val filePath: String,
    val fileName: String,
    val accessType: String,
    val fileSize: Long,
    val fileType: String,
    val accessTime: Long,
    val timestamp: Long
)

@Entity(tableName = "telegram_messages")
data class TelegramMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chatId: String,
    val message: String,
    val timestamp: Long,
    val sent: Boolean
)
