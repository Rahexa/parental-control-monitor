package com.family.safety.helper.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(
    entities = [
        MonitoringDataEntity::class,
        NotificationEntity::class,
        LocationEntity::class,
        MediaFileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MonitoringDatabase : RoomDatabase() {
    
    abstract fun monitoringDao(): MonitoringDao
    abstract fun notificationDao(): NotificationDao
    abstract fun locationDao(): LocationDao
    abstract fun mediaFileDao(): MediaFileDao
    
    companion object {
        @Volatile
        private var INSTANCE: MonitoringDatabase? = null
        
        fun getDatabase(context: Context): MonitoringDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MonitoringDatabase::class.java,
                    "monitoring_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
