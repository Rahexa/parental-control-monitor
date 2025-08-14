package com.parentalcontrol.monitor.models

data class DeviceInfo(
    val model: String,
    val batteryLevel: Int,
    val manufacturer: String,
    val version: String
)
