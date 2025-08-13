package com.parentalcontrol.monitor.services

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat

class HuaweiKeepAliveJobService : JobService() {
    
    companion object {
        private const val JOB_ID = 9999
        private const val JOB_INTERVAL = 15 * 60 * 1000L // 15 minutes
        
        fun scheduleJob(context: Context) {
            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            
            val jobInfo = JobInfo.Builder(JOB_ID, ComponentName(context, HuaweiKeepAliveJobService::class.java))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                .setPersisted(true)
                .setPeriodic(JOB_INTERVAL)
                .setRequiresCharging(false)
                .setRequiresDeviceIdle(false)
                .build()
            
            jobScheduler.schedule(jobInfo)
        }
        
        fun cancelJob(context: Context) {
            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancel(JOB_ID)
        }
    }
    
    override fun onStartJob(params: JobParameters?): Boolean {
        // Check if monitoring service is running
        if (!isMonitoringServiceRunning()) {
            // Restart monitoring service
            val intent = Intent(this, MonitoringService::class.java)
            ContextCompat.startForegroundService(this, intent)
        }
        
        // Check and restart other services if needed
        restartOtherServicesIfNeeded()
        
        // Job completed
        jobFinished(params, false)
        return true
    }
    
    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }
    
    private fun isMonitoringServiceRunning(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        
        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (MonitoringService::class.java.name == service.service.className) {
                return true
            }
        }
        return false
    }
    
    private fun restartOtherServicesIfNeeded() {
        try {
            // Restart location service if needed
            val locationIntent = Intent(this, LocationService::class.java)
            ContextCompat.startForegroundService(this, locationIntent)
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
