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
        
        val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED
        )
        
        val ANDROID_13_PERMISSIONS = arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
        )
    }
    
    fun requestAllPermissions(callback: (Boolean) -> Unit) {
        val permissionsToRequest = if (android.os.Build.VERSION.SDK_INT >= 33) {
            REQUIRED_PERMISSIONS + ANDROID_13_PERMISSIONS
        } else {
            REQUIRED_PERMISSIONS
        }
        
        Dexter.withActivity(activity)
            .withPermissions(*permissionsToRequest)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    callback(report?.areAllPermissionsGranted() == true)
                }
                
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .check()
    }
    
    fun hasAllPermissions(): Boolean {
        val permissionsToCheck = if (android.os.Build.VERSION.SDK_INT >= 33) {
            REQUIRED_PERMISSIONS + ANDROID_13_PERMISSIONS
        } else {
            REQUIRED_PERMISSIONS
        }
        
        return permissionsToCheck.all { permission ->
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
