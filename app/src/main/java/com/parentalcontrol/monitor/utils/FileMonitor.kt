package com.parentalcontrol.monitor.utils

import android.os.FileObserver
import java.io.File

class FileMonitor(
    private val path: String,
    private val onFileEvent: (event: Int, path: String?) -> Unit
) : FileObserver(File(path), ALL_EVENTS) {
    
    override fun onEvent(event: Int, path: String?) {
        onFileEvent(event, path)
    }
    
    companion object {
        const val FILE_CREATED = CREATE
        const val FILE_DELETED = DELETE
        const val FILE_MODIFIED = MODIFY
        const val FILE_MOVED_FROM = MOVED_FROM
        const val FILE_MOVED_TO = MOVED_TO
    }
}
