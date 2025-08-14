package com.parentalcontrol.monitor.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_usage")
data class AppUsageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val appName: String,
    val usageTime: Long,
    val timestamp: Long
)

@Entity(tableName = "file_access")
data class FileAccessEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fileName: String,
    val filePath: String,
    val accessType: String,
    val timestamp: Long
)

@Entity(tableName = "telegram_messages")
data class TelegramMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chatId: String,
    val text: String,
    val sent: Boolean = false,
    val timestamp: Long
)
