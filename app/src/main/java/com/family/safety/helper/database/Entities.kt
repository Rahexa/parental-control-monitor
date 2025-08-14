package com.family.safety.helper.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monitoring_data")
data class MonitoringDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String, // "app_usage", "file_access", "notification", "location", "call", "sms"
    val data: String, // JSON serialized data
    val timestamp: Long,
    val isSent: Boolean = false
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val appName: String,
    val title: String,
    val text: String,
    val timestamp: Long,
    val isSent: Boolean = false
)

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val timestamp: Long,
    val address: String? = null,
    val isSent: Boolean = false
)

@Entity(tableName = "media_files")
data class MediaFileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fileName: String,
    val filePath: String,
    val fileSize: Long,
    val fileType: String,
    val dateAdded: Long,
    val timestamp: Long,
    val isSent: Boolean = false
)
