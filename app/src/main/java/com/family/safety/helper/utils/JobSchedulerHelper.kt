package com.family.safety.helper.utils

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import com.family.safety.helper.services.HuaweiKeepAliveJobService

object JobSchedulerHelper {
    
    private const val JOB_ID = 1001
    
    fun scheduleKeepAliveJob(context: Context) {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        
        val jobInfo = JobInfo.Builder(JOB_ID, ComponentName(context, HuaweiKeepAliveJobService::class.java))
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
            .setPersisted(true)
            .setPeriodic(15 * 60 * 1000) // 15 minutes
            .setRequiresCharging(false)
            .setRequiresDeviceIdle(false)
            .build()
            
        jobScheduler.schedule(jobInfo)
    }
    
    fun cancelKeepAliveJob(context: Context) {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(JOB_ID)
    }
}
