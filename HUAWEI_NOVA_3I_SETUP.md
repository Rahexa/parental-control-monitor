# Huawei Nova 3i Setup Guide

## Device Specifications
- **Model**: Huawei Nova 3i
- **Android Version**: 8.1 (API 27)
- **EMUI Version**: 8.2
- **Processor**: Kirin 710
- **RAM**: 4GB

## Critical Setup Steps for Huawei Nova 3i

### 1. Phone Manager Protection
```
1. Open "Phone Manager" app
2. Tap "Protected apps"
3. Find "Parental Control Monitor"
4. Toggle switch to enable protection
```

### 2. Auto-start Management
```
1. Phone Manager → Auto-start
2. Find "Parental Control Monitor"
3. Enable auto-start toggle
```

### 3. App Launch Management
```
1. Settings → Apps & notifications
2. Find "Parental Control Monitor"
3. Tap "App launch"
4. Disable "Manage automatically"
5. Manually enable:
   - Auto-launch ✓
   - Secondary launch ✓
   - Run in background ✓
```

### 4. Battery Optimization
```
1. Settings → Battery
2. Tap "App launch"
3. Find "Parental Control Monitor"
4. Set to "Manual management"
5. Enable all three options
```

### 5. Lock in Recent Apps
```
1. Open recent apps (square navigation button)
2. Find "Parental Control Monitor"
3. Pull down on the app card
4. Tap the lock icon (🔒)
```

### 6. Notification Access
```
1. Settings → Apps & notifications
2. Special access → Notification access
3. Find "Parental Control Monitor"
4. Enable notification access
```

### 7. Accessibility Service
```
1. Settings → Accessibility
2. Find "Parental Control Monitor"
3. Enable the accessibility service
4. Confirm in popup dialog
```

### 8. Usage Stats Permission
```
1. Settings → Apps & notifications
2. Special access → Usage access
3. Find "Parental Control Monitor"
4. Enable usage access
```

### 9. Location Permissions
```
1. Settings → Apps & notifications
2. Find "Parental Control Monitor"
3. Permissions → Location
4. Select "Allow all the time"
```

### 10. Storage Permissions
```
1. Settings → Apps & notifications
2. Find "Parental Control Monitor"
3. Permissions → Storage
4. Enable storage permission
```

## EMUI-Specific Optimizations

### Power Genie Settings
```
1. Settings → Battery
2. Power saving mode → OFF
3. Performance mode → Performance
```

### Memory Cleanup Whitelist
```
1. Phone Manager → Cleanup
2. Memory cleanup → Settings
3. Add app to whitelist
```

### Network Management
```
1. Settings → Wireless & networks
2. Data usage → Smart data saver
3. Add app to unrestricted list
```

## Troubleshooting Common Issues

### Issue: Service Stops After Screen Lock
**Solution:**
1. Settings → Battery → App launch
2. Find app → Manual management
3. Enable "Run in background"

### Issue: Notifications Not Captured
**Solution:**
1. Reset notification access permission
2. Settings → Apps → Reset preferences
3. Re-enable notification access

### Issue: Location Not Working
**Solution:**
1. Check Google Play Services is updated
2. Enable high accuracy location mode
3. Disable battery optimization for Google Play Services

### Issue: App Disappears from Recent Apps
**Solution:**
1. Lock app in recent apps menu
2. Enable in Protected apps
3. Set app launch to manual management

## Verification Steps

After setup, verify:
1. ✅ App appears in Protected apps list
2. ✅ Auto-start is enabled
3. ✅ App launch set to manual with all options enabled
4. ✅ Locked in recent apps
5. ✅ Notification access granted
6. ✅ Accessibility service enabled
7. ✅ Location permission set to "Always"
8. ✅ Battery optimization disabled

## Expected Behavior

Once properly configured:
- App will survive device reboots
- Service will continue running in background
- Notifications will be captured and forwarded
- Location tracking will work continuously
- App will not be killed by EMUI's aggressive power management

## Notes for Huawei Nova 3i

- EMUI 8.2 is particularly aggressive with background apps
- The Kirin 710 processor has good power efficiency
- 4GB RAM should be sufficient for background operation
- Always test after each setup step to ensure functionality
