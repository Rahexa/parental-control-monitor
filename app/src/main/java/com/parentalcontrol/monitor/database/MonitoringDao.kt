package com.parentalcontrol.monitor.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AppUsageDao {
    @Insert
    suspend fun insertAppUsage(appUsage: AppUsageEntity)
    
    @Query("SELECT * FROM app_usage ORDER BY timestamp DESC")
    suspend fun getAllAppUsage(): List<AppUsageEntity>
}

@Dao
interface FileAccessDao {
    @Insert
    suspend fun insertFileAccess(fileAccess: FileAccessEntity)
    
    @Query("SELECT * FROM file_access ORDER BY timestamp DESC")
    suspend fun getAllFileAccess(): List<FileAccessEntity>
}

@Dao
interface TelegramMessageDao {
    @Insert
    suspend fun insertMessage(message: TelegramMessageEntity)
    
    @Query("SELECT * FROM telegram_messages WHERE sent = 0")
    suspend fun getUnsentMessages(): List<TelegramMessageEntity>
    
    @Query("UPDATE telegram_messages SET sent = 1 WHERE id = :id")
    suspend fun markAsSent(id: Long)
}
