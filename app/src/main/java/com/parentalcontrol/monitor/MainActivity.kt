package com.parentalcontrol.monitor

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.parentalcontrol.monitor.databinding.ActivityMainBinding
import com.parentalcontrol.monitor.services.MonitoringService
import com.parentalcontrol.monitor.services.TelegramService
import com.parentalcontrol.monitor.utils.HuaweiOptimizationHelper
import com.parentalcontrol.monitor.utils.PermissionHelper

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionHelper: PermissionHelper
    private lateinit var huaweiHelper: HuaweiOptimizationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        permissionHelper = PermissionHelper(this)
        huaweiHelper = HuaweiOptimizationHelper(this)
        
        setupUI()
        checkPermissions()
        
        // Check for Huawei-specific optimizations
        if (huaweiHelper.isHuaweiDevice()) {
            checkHuaweiOptimizations()
        }
    }
    
    private fun checkHuaweiOptimizations() {
        val issues = huaweiHelper.checkHuaweiOptimizations()
        if (issues.isNotEmpty()) {
            binding.tvHuaweiOptimization.visibility = android.view.View.VISIBLE
            binding.tvHuaweiOptimization.text = "⚠️ Huawei optimizations needed: ${issues.size} issues"
            binding.btnHuaweiOptimization.visibility = android.view.View.VISIBLE
        } else {
            binding.tvHuaweiOptimization.visibility = android.view.View.GONE
            binding.btnHuaweiOptimization.visibility = android.view.View.GONE
        }
    }
    
    private fun setupUI() {
        binding.apply {
            btnStartMonitoring.setOnClickListener {
                if (hasRequiredPermissions()) {
                    startMonitoringService()
                } else {
                    requestPermissions()
                }
            }
            
            btnStopMonitoring.setOnClickListener {
                stopMonitoringService()
            }
            
            btnConfigureTelegram.setOnClickListener {
                configureTelegram()
            }
            
            btnRequestPermissions.setOnClickListener {
                requestPermissions()
            }
            
            btnHuaweiOptimization.setOnClickListener {
                huaweiHelper.requestHuaweiOptimizations()
            }
            
            btnHuaweiInstructions.setOnClickListener {
                showHuaweiInstructions()
            }
        }
    }
    
    private fun showHuaweiInstructions() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Huawei Nova 3i Setup Guide")
            .setMessage(huaweiHelper.getHuaweiSpecificInstructions())
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
    
    private fun checkPermissions() {
        val hasUsageAccess = hasUsageStatsPermission()
        val hasAccessibility = isAccessibilityServiceEnabled()
        
        binding.apply {
            tvUsagePermissionStatus.text = if (hasUsageAccess) "✓ Granted" else "✗ Required"
            tvAccessibilityStatus.text = if (hasAccessibility) "✓ Enabled" else "✗ Required"
            
            btnStartMonitoring.isEnabled = hasUsageAccess && hasAccessibility
            
            // Update Huawei-specific status
            if (huaweiHelper.isHuaweiDevice()) {
                tvDeviceInfo.text = "Device: Huawei Nova 3i (EMUI Optimizations Required)"
                tvDeviceInfo.visibility = android.view.View.VISIBLE
            }
        }
    }
    
    private fun hasRequiredPermissions(): Boolean {
        return hasUsageStatsPermission() && isAccessibilityServiceEnabled()
    }
    
    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }
    
    private fun isAccessibilityServiceEnabled(): Boolean {
        val accessibilityServiceName = "${packageName}/.services.AccessibilityMonitorService"
        val enabledServices = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        return enabledServices?.contains(accessibilityServiceName) == true
    }
    
    private fun requestPermissions() {
        if (!hasUsageStatsPermission()) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }
        
        if (!isAccessibilityServiceEnabled()) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
    }
    
    private fun startMonitoringService() {
        val intent = Intent(this, MonitoringService::class.java)
        ContextCompat.startForegroundService(this, intent)
        
        binding.tvMonitoringStatus.text = "Monitoring Active"
        Toast.makeText(this, "Monitoring started", Toast.LENGTH_SHORT).show()
    }
    
    private fun stopMonitoringService() {
        val intent = Intent(this, MonitoringService::class.java)
        stopService(intent)
        
        binding.tvMonitoringStatus.text = "Monitoring Stopped"
        Toast.makeText(this, "Monitoring stopped", Toast.LENGTH_SHORT).show()
    }
    
    private fun configureTelegram() {
        // TODO: Implement Telegram configuration dialog
        val sharedPrefs = getSharedPreferences("telegram_config", Context.MODE_PRIVATE)
        val botToken = sharedPrefs.getString("bot_token", "")
        val chatId = sharedPrefs.getString("chat_id", "")
        
        if (botToken.isNullOrEmpty() || chatId.isNullOrEmpty()) {
            // Show configuration dialog
            showTelegramConfigDialog()
        } else {
            Toast.makeText(this, "Telegram already configured", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showTelegramConfigDialog() {
        // TODO: Implement configuration dialog
        Toast.makeText(this, "Please configure Telegram bot token and chat ID", Toast.LENGTH_LONG).show()
    }
    
    override fun onResume() {
        super.onResume()
        checkPermissions()
    }
}
