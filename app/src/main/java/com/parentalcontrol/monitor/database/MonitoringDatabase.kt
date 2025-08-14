package com.parentalcontrol.monitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
abstract class MonitoringDatabase : RoomDatabase() {
    
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
