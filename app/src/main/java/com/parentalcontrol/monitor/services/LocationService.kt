package com.parentalcontrol.monitor.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class LocationService : Service() {
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onCreate() {
        super.onCreate()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
    
    override fun onDestroy() {
        super.onDestroy()
    }
}
