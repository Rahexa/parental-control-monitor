# Parental Control Monitor - Testing Checklist

## Pre-Installation Setup
- [ ] Enable Developer Options on test device
- [ ] Enable USB Debugging
- [ ] Enable "Unknown Sources" for APK installation
- [ ] Download APK from GitHub Actions artifacts

## Installation Testing
- [ ] Install debug APK successfully
- [ ] App launches without crashes
- [ ] AutoSetupActivity opens on first run
- [ ] App icon appears in launcher (before hiding)

## Permission Testing
- [ ] Accessibility Service permission requested
- [ ] Notification Access permission requested
- [ ] Location permission requested
- [ ] Phone permission requested
- [ ] SMS permission requested
- [ ] Device Admin permission requested
- [ ] Battery optimization disabled (Huawei devices)

## Service Testing
- [ ] MonitoringService starts and runs in background
- [ ] TelegramService initializes correctly
- [ ] LocationService tracks GPS coordinates
- [ ] AccessibilityMonitorService monitors app usage
- [ ] Services persist after device reboot

## Telegram Integration Testing
- [ ] Bot token configured from BuildConfig
- [ ] Chat ID configured from BuildConfig
- [ ] Setup confirmation message sent
- [ ] App usage reports sent to Telegram
- [ ] Location updates sent to Telegram
- [ ] Call/SMS notifications sent to Telegram

## Monitoring Features Testing
- [ ] App usage time tracking works
- [ ] Social media app detection works
- [ ] File access monitoring works
- [ ] Call monitoring and logging works
- [ ] SMS monitoring and logging works
- [ ] Location tracking accuracy
- [ ] Notification interception works

## Stealth Mode Testing
- [ ] App hides from launcher after setup (if AUTO_HIDE=true)
- [ ] App continues running in background
- [ ] App survives device restarts
- [ ] App not visible in recent apps
- [ ] No obvious notifications shown

## Huawei Device Specific Testing
- [ ] Battery optimization disabled
- [ ] Protected apps configuration
- [ ] Auto-start configuration
- [ ] Background app management configured

## Error Handling Testing
- [ ] App handles no internet connection
- [ ] App handles Telegram API failures
- [ ] App handles permission denials gracefully
- [ ] App handles service crashes and restarts
- [ ] App handles invalid configuration

## Performance Testing
- [ ] Battery usage is reasonable
- [ ] Memory usage is acceptable
- [ ] CPU usage doesn't cause device lag
- [ ] Storage usage is minimal
- [ ] Network usage is efficient

## Security Testing
- [ ] Sensitive data is encrypted
- [ ] Telegram tokens are secure
- [ ] User data is protected
- [ ] No unnecessary permissions requested
- [ ] Compliance with privacy regulations

## Notes:
- Test on Android API 27+ (minimum supported)
- Test on different device manufacturers (especially Huawei)
- Monitor device logs using: `adb logcat | grep ParentalControl`
- Check app database: `/data/data/com.parentalcontrol.monitor/databases/`
