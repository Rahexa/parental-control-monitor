package com.parentalcontrol.monitor.services

import android.app.job.JobParameters
import android.app.job.JobService

class HuaweiKeepAliveJobService : JobService() {
    
    override fun onStartJob(params: JobParameters?): Boolean {
        return false
    }
    
    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }
}
