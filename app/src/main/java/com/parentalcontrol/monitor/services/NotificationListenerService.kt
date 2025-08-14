package com.parentalcontrol.monitor.services

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationListenerService : NotificationListenerService() {
    
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        // Handle notification posted
    }
    
    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        // Handle notification removed
    }
}