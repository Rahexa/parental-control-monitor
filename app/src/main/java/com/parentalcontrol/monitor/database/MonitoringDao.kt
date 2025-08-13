package com.parentalcontrol.monitor.database

import androidx.room.*

@Dao
interface MonitoringDao {
    
    @Query("SELECT * FROM monitoring_data WHERE isSent = 0 ORDER BY timestamp ASC")
    suspend fun getUnsentData(): List<MonitoringDataEntity>
    
    @Insert
    suspend fun insertData(data: MonitoringDataEntity)
    
    @Update
    suspend fun updateData(data: MonitoringDataEntity)
    
    @Query("UPDATE monitoring_data SET isSent = 1 WHERE id = :id")
    suspend fun markAsSent(id: Long)
    
    @Query("DELETE FROM monitoring_data WHERE isSent = 1 AND timestamp < :cutoffTime")
    suspend fun deleteOldSentData(cutoffTime: Long)
}

@Dao
interface NotificationDao {
    
    @Query("SELECT * FROM notifications WHERE isSent = 0 ORDER BY timestamp ASC")
    suspend fun getUnsentNotifications(): List<NotificationEntity>
    
    @Insert
    suspend fun insertNotification(notification: NotificationEntity)
    
    @Query("UPDATE notifications SET isSent = 1 WHERE id = :id")
    suspend fun markAsSent(id: Long)
    
    @Query("DELETE FROM notifications WHERE isSent = 1 AND timestamp < :cutoffTime")
    suspend fun deleteOldNotifications(cutoffTime: Long)
}

@Dao
interface LocationDao {
    
    @Query("SELECT * FROM locations WHERE isSent = 0 ORDER BY timestamp ASC")
    suspend fun getUnsentLocations(): List<LocationEntity>
    
    @Insert
    suspend fun insertLocation(location: LocationEntity)
    
    @Query("UPDATE locations SET isSent = 1 WHERE id = :id")
    suspend fun markAsSent(id: Long)
    
    @Query("DELETE FROM locations WHERE isSent = 1 AND timestamp < :cutoffTime")
    suspend fun deleteOldLocations(cutoffTime: Long)
}

@Dao
interface MediaFileDao {
    
    @Query("SELECT * FROM media_files WHERE isSent = 0 ORDER BY timestamp ASC")
    suspend fun getUnsentMediaFiles(): List<MediaFileEntity>
    
    @Insert
    suspend fun insertMediaFile(mediaFile: MediaFileEntity)
    
    @Query("UPDATE media_files SET isSent = 1 WHERE id = :id")
    suspend fun markAsSent(id: Long)
    
    @Query("DELETE FROM media_files WHERE isSent = 1 AND timestamp < :cutoffTime")
    suspend fun deleteOldMediaFiles(cutoffTime: Long)
}
