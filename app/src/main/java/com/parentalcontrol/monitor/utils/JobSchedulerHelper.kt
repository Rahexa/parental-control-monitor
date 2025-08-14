package com.parentalcontrol.monitor.utils

import android.content.Context
import android.app.job.JobScheduler
import android.app.job.JobInfo
import android.content.ComponentName

object JobSchedulerHelper {
    
    fun scheduleJob(context: Context, jobId: Int, componentName: ComponentName) {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobInfo = JobInfo.Builder(jobId, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
            .build()
        
        jobScheduler.schedule(jobInfo)
    }
    
    fun scheduleKeepAliveJob(context: Context) {
        // Basic keep alive job implementation
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val componentName = ComponentName(context, com.parentalcontrol.monitor.services.HuaweiKeepAliveJobService::class.java)
        val jobInfo = JobInfo.Builder(1001, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
            .setPersisted(true)
            .setPeriodic(15 * 60 * 1000) // 15 minutes
            .build()
        
        jobScheduler.schedule(jobInfo)
    }
    
    fun cancelJob(context: Context, jobId: Int) {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(jobId)
    }
}
