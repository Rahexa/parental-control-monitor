# GitHub Actions CI/CD Pipeline ✅

Your Android parental control app now has a complete CI/CD pipeline set up with GitHub Actions!

## 🚀 What's Included

### Workflows Created:
- **`android-build.yml`** - Full CI/CD pipeline (builds, tests, releases)
- **`quick-build.yml`** - Fast development builds
- **`release.yml`** - Production release pipeline

### Build Features:
- ✅ Automatic APK building (debug & release)
- ✅ Unit testing
- ✅ Security scanning (CodeQL)
- ✅ APK signing for releases
- ✅ Automatic GitHub releases
- ✅ Build caching for speed
- ✅ Telegram credentials from environment

## 📦 Quick Start

### 1. Push to GitHub
```bash
git add .
git commit -m "Add GitHub Actions CI/CD pipeline"
git push origin main
```

### 2. Configure Secrets
Go to: `Repository Settings → Secrets and variables → Actions`

Add these secrets:
```
TELEGRAM_BOT_TOKEN=your_bot_token_here
TELEGRAM_CHAT_ID=your_chat_id_here
```

### 3. Generate Signing Key (Optional - for releases)
```bash
# Windows
generate-signing-key.bat

# Linux/macOS
chmod +x generate-signing-key.sh
./generate-signing-key.sh
```

### 4. Watch the Magic! 🎉
- Push code → Automatic build starts
- Merge to main → Creates release with signed APK
- All builds available in Actions tab

## 🔧 How It Works

### Development Flow:
```
feature branch → push → quick-build.yml → APK artifact
```

### Release Flow:
```
main branch → push → android-build.yml → signed APK + GitHub release
```

### Manual Release:
```
Actions tab → Release Build → Run workflow → tagged release
```

## 📱 APK Downloads

After each build:
1. Go to **Actions** tab in your repository
2. Click on any workflow run
3. Download APK from **Artifacts** section

For releases:
1. Go to **Releases** section
2. Download signed APK from latest release

## 🛠️ Environment Variables

The build system now supports environment-based configuration:

```kotlin
// In your app code
val botToken = BuildConfig.TELEGRAM_BOT_TOKEN
val chatId = BuildConfig.TELEGRAM_CHAT_ID
val autoHide = BuildConfig.AUTO_HIDE
```

## 📋 Build Status

Add this badge to your main README:
```markdown
![Android CI](https://github.com/Rahexa/parental-control-monitor/workflows/Android%20CI%2FCD/badge.svg)
```

## 🔐 Security Features

- Encrypted secrets in GitHub
- CodeQL security scanning
- Signed APKs for production
- No sensitive data in logs
- Environment-based configuration

## 📚 Documentation

- **`GITHUB_ACTIONS_SETUP.md`** - Detailed setup guide
- **Workflow files** - In `.github/workflows/` directory
- **Signing scripts** - `generate-signing-key.bat/.sh`

## 🎯 Next Steps

1. **Test the pipeline** - Push a commit and watch it build
2. **Configure Telegram secrets** - Add your bot credentials
3. **Generate signing key** - For production releases
4. **Create releases** - Merge to main or run manual release

Your app will now build automatically on every push! 🚀
