package com.parentalcontrol.monitor.services

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.IBinder
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.parentalcontrol.monitor.database.LocationEntity
import com.parentalcontrol.monitor.database.MonitoringDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class LocationService : Service() {
    
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var database: MonitoringDatabase
    private lateinit var telegramService: TelegramService
    private lateinit var geocoder: Geocoder
    private val scope = CoroutineScope(Dispatchers.IO)
    
    companion object {
        private const val LOCATION_UPDATE_INTERVAL = 5 * 60 * 1000L // 5 minutes
        private const val FASTEST_UPDATE_INTERVAL = 2 * 60 * 1000L // 2 minutes
    }
    
    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        database = MonitoringDatabase.getDatabase(this)
        telegramService = TelegramService()
        telegramService.initialize(this)
        geocoder = Geocoder(this, Locale.getDefault())
        
        setupLocationCallback()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startLocationUpdates()
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    processLocation(location)
                }
            }
        }
    }
    
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            LOCATION_UPDATE_INTERVAL
        ).apply {
            setMinUpdateIntervalMillis(FASTEST_UPDATE_INTERVAL)
            setMaxUpdateDelayMillis(LOCATION_UPDATE_INTERVAL * 2)
        }.build()
        
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
    
    private fun processLocation(location: Location) {
        scope.launch {
            try {
                val address = getAddressFromLocation(location)
                
                val locationEntity = LocationEntity(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    accuracy = location.accuracy,
                    timestamp = System.currentTimeMillis(),
                    address = address
                )
                
                database.locationDao().insertLocation(locationEntity)
                
                // Try to send immediately if online
                val success = telegramService.sendLocationUpdate(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    accuracy = location.accuracy,
                    address = address,
                    timestamp = locationEntity.timestamp
                )
                
                if (success) {
                    database.locationDao().markAsSent(locationEntity.id)
                }
                
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    private suspend fun getAddressFromLocation(location: Location): String? {
        return try {
            val addresses: List<Address>? = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
            
            addresses?.firstOrNull()?.let { address ->
                buildString {
                    if (address.thoroughfare != null) append("${address.thoroughfare}, ")
                    if (address.locality != null) append("${address.locality}, ")
                    if (address.adminArea != null) append("${address.adminArea}, ")
                    if (address.countryName != null) append(address.countryName)
                }
            }
        } catch (e: Exception) {
            null
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
    
    fun updateLocationFrequency(intervalMinutes: Int) {
        val newInterval = intervalMinutes * 60 * 1000L
        fusedLocationClient.removeLocationUpdates(locationCallback)
        
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            newInterval
        ).apply {
            setMinUpdateIntervalMillis(newInterval / 2)
            setMaxUpdateDelayMillis(newInterval * 2)
        }.build()
        
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }
}
