package com.parentalcontrol.monitor.services

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class AccessibilityMonitorService : AccessibilityService() {
    
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Handle accessibility events
    }
    
    override fun onInterrupt() {
        // Handle service interruption
    }
    
    override fun onServiceConnected() {
        super.onServiceConnected()
        // Service connected
    }
}
