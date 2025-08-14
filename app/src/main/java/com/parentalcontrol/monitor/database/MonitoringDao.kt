package com.parentalcontrol.monitor.database

import androidx.room.*

@Dao
interface MonitoringDao {
    
    @Query("SELECT * FROM app_usage ORDER BY timestamp DESC")
    suspend fun getAllAppUsage(): List<AppUsageEntity>
    
    @Insert
    suspend fun insertAppUsage(usage: AppUsageEntity)
    
    @Query("SELECT * FROM file_access ORDER BY timestamp DESC")
    suspend fun getAllFileAccess(): List<FileAccessEntity>
    
    @Insert
    suspend fun insertFileAccess(access: FileAccessEntity)
    
    @Query("SELECT * FROM telegram_messages WHERE sent = 0")
    suspend fun getUnsentMessages(): List<TelegramMessageEntity>
    
    @Insert
    suspend fun insertTelegramMessage(message: TelegramMessageEntity)
    
    @Update
    suspend fun updateTelegramMessage(message: TelegramMessageEntity)
}
