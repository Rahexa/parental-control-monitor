# GitHub Actions Deployment Guide

This repository is set up with automated build and deployment using GitHub Actions. Here's how to use it:

## 🚀 Available Workflows

### 1. **Automatic Builds** (`android.yml`)
- **Triggers**: Push to main/master/develop, Pull requests
- **Builds**: Debug and Release APKs
- **Artifacts**: Available for 30 days
- **Tests**: Runs unit tests and lint checks

### 2. **Release Builds** (`release.yml`)
- **Triggers**: When you create a tag (e.g., `v1.0.0`)
- **Creates**: GitHub release with downloadable APKs
- **Includes**: Changelog, setup guide, both debug and release APKs

### 3. **Nightly Builds** (`nightly.yml`)
- **Triggers**: Daily at 2 AM UTC, or manual trigger
- **Purpose**: Latest development version
- **Warning**: Development builds, may contain bugs

### 4. **Manual Builds** (`manual-build.yml`)
- **Triggers**: Manual trigger from GitHub Actions tab
- **Options**: Choose debug/release/both, custom version name
- **Flexible**: Build on-demand with custom settings

## 📥 How to Download APKs

### Method 1: From Releases (Recommended)
1. Go to the [Releases page](../../releases)
2. Download the latest release APK
3. Install on your Huawei Nova 3i

### Method 2: From Actions Artifacts
1. Go to the [Actions tab](../../actions)
2. Click on a completed workflow run
3. Scroll down to "Artifacts" section
4. Download the APK zip file
5. Extract and install the APK

### Method 3: Manual Build
1. Go to [Actions tab](../../actions)
2. Click "Manual Build" workflow
3. Click "Run workflow"
4. Choose your build options
5. Download from artifacts when complete

## 🏷️ Creating a Release

To create a new release with automatic APK builds:

```bash
# Create and push a tag
git tag v1.0.0
git push origin v1.0.0
```

This will automatically:
- Build both debug and release APKs
- Create a GitHub release
- Generate changelog
- Upload APKs as release assets
- Include setup guide

## 🔧 Setting Up Repository

### 1. **Push to GitHub**
```bash
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/parental-control-monitor.git
git push -u origin main
```

### 2. **Enable Actions**
- GitHub Actions are automatically enabled
- Workflows will run on first push

### 3. **Optional: Add Signing (for Release)**
If you want signed APKs, add these secrets in repository settings:
- `SIGNING_KEY`: Base64 encoded keystore file
- `ALIAS`: Keystore alias
- `KEY_STORE_PASSWORD`: Keystore password
- `KEY_PASSWORD`: Key password

## 📱 Installation Instructions

### For Huawei Nova 3i:

1. **Download APK**
   - Release APK for production use
   - Debug APK for testing

2. **Enable Unknown Sources**
   ```
   Settings → Security → Unknown sources → Enable
   ```

3. **Install APK**
   - Open downloaded APK file
   - Follow installation prompts

4. **Complete Setup**
   - Open app and follow Huawei optimization guide
   - Configure Telegram bot
   - Grant all required permissions

## 🔄 Build Status

| Workflow | Status | Purpose |
|----------|--------|---------|
| Android CI/CD | ![Android CI](../../actions/workflows/android.yml/badge.svg) | Main build pipeline |
| Release | ![Release](../../actions/workflows/release.yml/badge.svg) | Tagged releases |
| Nightly | ![Nightly](../../actions/workflows/nightly.yml/badge.svg) | Development builds |
| Manual Build | ![Manual](../../actions/workflows/manual-build.yml/badge.svg) | On-demand builds |

## 🎯 Quick Start

1. **Immediate APK**: Use "Manual Build" workflow
2. **Latest Stable**: Check Releases page
3. **Development Version**: Download from latest main branch build
4. **Custom Build**: Fork repository and run your own workflows

## 📚 Additional Resources

- [Huawei Nova 3i Setup Guide](HUAWEI_NOVA_3I_SETUP.md)
- [Main README](README.md)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)

## ⚠️ Important Notes

- **Debug APKs**: For testing, easier to install
- **Release APKs**: For production, optimized but unsigned
- **Signed APKs**: Require keystore setup (optional)
- **Huawei Devices**: Follow device-specific setup guide

Your APKs will be available for download immediately after pushing to GitHub! 🚀
