package com.parentalcontrol.monitor.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PermissionHelper(private val activity: Activity) {
    
    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
        
        // Core permissions including monitoring capabilities
        val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        
        // Additional permissions for enhanced monitoring
        val OPTIONAL_PERMISSIONS = arrayOf(
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_CALENDAR
        )
        
        fun hasLocationPermission(context: Context): Boolean {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                   ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }
        
        fun isNotificationAccessGranted(context: Context): Boolean {
            // This requires special permission check
            return true // Simplified for now
        }
        
        fun isAccessibilityServiceEnabled(context: Context): Boolean {
            // This requires special permission check
            return true // Simplified for now  
        }
    }
    
    fun requestAllPermissions(callback: (Boolean) -> Unit) {
        // Request comprehensive family monitoring permissions
        Dexter.withActivity(activity)
            .withPermissions(*REQUIRED_PERMISSIONS)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    // Return actual permission status but continue setup
                    val allGranted = report?.areAllPermissionsGranted() == true
                    callback(allGranted)
                }
                
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    // Show rationale for family safety monitoring
                    token?.continuePermissionRequest()
                }
            })
            .check()
    }
    
    fun requestOptionalPermissions(callback: (Boolean) -> Unit) {
        // Request additional permissions for comprehensive monitoring
        Dexter.withActivity(activity)
            .withPermissions(*OPTIONAL_PERMISSIONS)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    // Return actual permission status
                    val allGranted = report?.areAllPermissionsGranted() == true
                    callback(allGranted)
                }
                
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    // Show rationale for additional monitoring capabilities
                    token?.continuePermissionRequest()
                }
            })
            .check()
    }
    
    fun hasAllPermissions(): Boolean {
        // Only check basic permissions
        return REQUIRED_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }
    
    fun requestPermission(permission: String) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission),
            PERMISSION_REQUEST_CODE
        )
    }
}
