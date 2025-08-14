package com.parentalcontrol.monitor.services

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class AccessibilityMonitorService : AccessibilityService() {
    
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Handle accessibility events
    }
    
    override fun onInterrupt() {
        // Handle service interrupt
    }
    
    override fun onCreate() {
        super.onCreate()
    }
    
    override fun onDestroy() {
        super.onDestroy()
    }
}
