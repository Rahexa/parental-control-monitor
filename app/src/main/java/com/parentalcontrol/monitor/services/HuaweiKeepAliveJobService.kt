package com.parentalcontrol.monitor.services

import android.app.job.JobParameters
import android.app.job.JobService

class HuaweiKeepAliveJobService : JobService() {
    
    override fun onStartJob(params: JobParameters?): Boolean {
        // Job to keep the app alive on Huawei devices
        // Return false since we don't need to do async work
        return false
    }
    
    override fun onStopJob(params: JobParameters?): Boolean {
        // Return true to reschedule the job
        return true
    }
}
