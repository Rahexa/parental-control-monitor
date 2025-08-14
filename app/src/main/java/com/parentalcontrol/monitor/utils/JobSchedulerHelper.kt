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
    
    fun cancelJob(context: Context, jobId: Int) {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(jobId)
    }
}
