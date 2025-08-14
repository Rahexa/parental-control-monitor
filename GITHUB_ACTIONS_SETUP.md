# GitHub Actions Setup Guide

## Overview
I've created a complete CI/CD pipeline for your Android parental control app with 3 workflows:

1. **Main Build (`android-build.yml`)** - Full build, test, and release pipeline
2. **Quick Build (`quick-build.yml`)** - Fast builds for development branches
3. **Release Build (`release.yml`)** - Production releases with signing

## Setup Steps

### 1. Repository Secrets Configuration

Go to your GitHub repository → Settings → Secrets and variables → Actions

Add these secrets:

#### Required for Telegram Integration:
```
TELEGRAM_BOT_TOKEN=your_actual_bot_token_here
TELEGRAM_CHAT_ID=your_actual_chat_id_here
```

#### Required for APK Signing (Production):
```
SIGNING_KEY=base64_encoded_keystore_file
KEY_ALIAS=your_key_alias
KEY_STORE_PASSWORD=your_keystore_password
KEY_PASSWORD=your_key_password
```

### 2. Generate Signing Key (For Production Releases)

```bash
# Generate a new keystore
keytool -genkey -v -keystore release-key.keystore -alias release -keyalg RSA -keysize 2048 -validity 10000

# Convert to base64 for GitHub secrets
base64 release-key.keystore > keystore.base64
```

Copy the base64 content to `SIGNING_KEY` secret.

### 3. Workflow Triggers

#### Main Build (`android-build.yml`):
- Triggers on push to `main` or `develop` branches
- Triggers on pull requests to `main`
- Manual trigger available
- Creates releases automatically on main branch

#### Quick Build (`quick-build.yml`):
- Triggers on push to `develop` or `feature/*` branches
- Fast builds without signing
- Manual trigger available

#### Release Build (`release.yml`):
- Triggers when you create a GitHub release
- Manual trigger with version input
- Signs APK and creates release

## Usage Examples

### 1. Development Workflow
```bash
# Create feature branch
git checkout -b feature/new-monitoring

# Make changes and push
git add .
git commit -m "Add new monitoring feature"
git push origin feature/new-monitoring

# This triggers quick-build.yml
# APK available in Actions artifacts
```

### 2. Release Workflow
```bash
# Merge to main triggers full build
git checkout main
git merge feature/new-monitoring
git push origin main

# This triggers android-build.yml
# Creates automatic release with signed APK
```

### 3. Manual Release
```bash
# Go to GitHub Actions → Release Build → Run workflow
# Enter version number (e.g., 1.2.0)
# Creates tagged release with signed APK
```

## Build Features

### ✅ Automatic APK Building
- Debug APK for development
- Release APK for production
- Automatic signing for releases

### ✅ Testing & Quality
- Unit tests execution
- CodeQL security analysis
- Gradle wrapper validation

### ✅ Caching & Performance
- Gradle cache for faster builds
- Parallel builds where possible
- Optimized for Android projects

### ✅ Artifacts & Releases
- APK artifacts for every build
- Automatic GitHub releases
- Detailed release notes

### ✅ Environment Support
- Telegram credentials from secrets
- BuildConfig fields for configuration
- Support for different build variants

## Files Created

```
.github/
├── workflows/
│   ├── android-build.yml    # Main CI/CD pipeline
│   ├── quick-build.yml      # Fast development builds
│   └── release.yml          # Production releases
```

## Next Steps

1. **Push to GitHub**: Commit and push these workflow files
2. **Configure Secrets**: Add the required secrets in repository settings
3. **Test Build**: Push a commit to trigger the first build
4. **Generate Keystore**: Create signing key for production releases

## Build Status

Once set up, you'll see build status badges in your repository. You can add this to your README:

```markdown
![Android CI](https://github.com/Rahexa/parental-control-monitor/workflows/Android%20CI%2FCD/badge.svg)
```

## Troubleshooting

### Common Issues:

1. **Build fails with missing secrets**
   - Ensure TELEGRAM_BOT_TOKEN and TELEGRAM_CHAT_ID are set
   - Check secret names match exactly

2. **Gradle build fails**
   - Check gradlew file permissions
   - Verify Android SDK setup in workflow

3. **Signing fails**
   - Verify all signing secrets are correctly set
   - Check keystore base64 encoding

### Build Logs:
- Go to GitHub Actions tab in your repository
- Click on any workflow run to see detailed logs
- Each step shows execution details and errors

## Security Considerations

- ✅ Secrets are encrypted in GitHub
- ✅ Environment variables only in trusted workflows
- ✅ CodeQL security scanning enabled
- ✅ Signed releases for production
- ✅ No secrets exposed in logs

Your Android app will now build automatically with professional CI/CD practices!
