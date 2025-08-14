package com.parentalcontrol.monitor.utils

import android.database.ContentObserver
import android.net.Uri
import android.os.Handler

class MediaObserver(
    handler: Handler,
    private val onMediaChange: () -> Unit
) : ContentObserver(handler) {
    
    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        onMediaChange()
    }
    
    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        onMediaChange()
    }
}
