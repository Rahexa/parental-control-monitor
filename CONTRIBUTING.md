# Contributing to Parental Control Monitor

Thank you for your interest in contributing to the Parental Control Monitor project! We welcome contributions from the community and are grateful for your support.

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [How to Contribute](#how-to-contribute)
- [Development Setup](#development-setup)
- [Coding Standards](#coding-standards)
- [Testing Guidelines](#testing-guidelines)
- [Submitting Changes](#submitting-changes)
- [Issue Guidelines](#issue-guidelines)
- [Security Guidelines](#security-guidelines)

## 📜 Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

### Our Standards

- **Be respectful** and inclusive in your communication
- **Be collaborative** and help others learn
- **Focus on constructive feedback** and solutions
- **Respect privacy and security** considerations
- **Follow ethical guidelines** for parental monitoring software

## 🚀 Getting Started

### Prerequisites

Before contributing, ensure you have:

- **Android Studio** 2023.1.1 or later
- **JDK 17** or later
- **Android SDK** with API levels 27-34
- **Git** for version control
- **Kotlin** development experience

### Setting Up Development Environment

1. **Fork the repository**
   ```bash
   git clone https://github.com/yourusername/parental-control-monitor.git
   cd parental-control-monitor
   ```

2. **Set up Android Studio**
   - Import the project
   - Sync Gradle files
   - Install required SDK components

3. **Configure local properties**
   ```properties
   # local.properties
   telegram.bot.token=your_test_bot_token
   telegram.chat.id=your_test_chat_id
   ```

## 🔧 How to Contribute

### Types of Contributions

We welcome these types of contributions:

- 🐛 **Bug fixes** - Fix issues and improve stability
- ✨ **New features** - Add monitoring capabilities
- 📚 **Documentation** - Improve guides and comments
- 🔧 **Performance** - Optimize battery usage and efficiency
- 🛡️ **Security** - Enhance privacy and data protection
- 📱 **Device compatibility** - Support more Android devices
- 🌐 **Localization** - Translate to other languages

### What We're Looking For

**High Priority:**
- Bug fixes for critical monitoring issues
- Battery optimization improvements
- Additional device-specific optimizations
- Enhanced stealth capabilities
- Security vulnerability fixes

**Medium Priority:**
- New monitoring features (app categories, time limits)
- UI/UX improvements for hidden interface
- Performance optimizations
- Documentation improvements

**Low Priority:**
- Code refactoring and cleanup
- Additional language support
- New alert types and notifications

## 💻 Development Setup

### Project Structure

```
parental-control-monitor/
├── app/src/main/java/com/parentalcontrol/monitor/
│   ├── activities/          # UI activities
│   ├── services/           # Background services
│   ├── receivers/          # Broadcast receivers
│   ├── database/           # Room database components
│   ├── utils/              # Utility classes
│   └── models/             # Data models
├── docs/                   # Documentation
├── .github/                # GitHub workflows and templates
└── gradle/                 # Gradle configuration
```

### Key Components

- **AutoSetupActivity** - Zero-configuration setup
- **MonitoringService** - Main background monitoring
- **TelegramService** - Bot communication
- **LocationService** - GPS tracking
- **NotificationListener** - System notification capture
- **AccessibilityMonitor** - App usage tracking

## 🎯 Coding Standards

### Kotlin Guidelines

```kotlin
// Use meaningful variable names
val notificationContent = notification.extras.getString(Notification.EXTRA_TEXT)

// Add documentation for complex functions
/**
 * Processes incoming notification and extracts relevant monitoring data
 * @param notification The StatusBarNotification to process
 * @return MonitoringData object with extracted information
 */
fun processNotification(notification: StatusBarNotification): MonitoringData {
    // Implementation
}

// Use proper error handling
try {
    val location = getCurrentLocation()
    sendLocationUpdate(location)
} catch (e: SecurityException) {
    Log.e(TAG, "Location permission not granted", e)
    requestLocationPermission()
} catch (e: Exception) {
    Log.e(TAG, "Failed to get location", e)
}
```

### Architecture Patterns

- **MVVM** for UI components
- **Repository pattern** for data management
- **Dependency injection** with Hilt
- **Coroutines** for asynchronous operations

### Code Style

- Follow **Android Kotlin Style Guide**
- Use **4 spaces** for indentation
- **Max line length** of 120 characters
- **Meaningful commit messages** following conventional commits

## 🧪 Testing Guidelines

### Testing Requirements

All contributions should include appropriate tests:

```kotlin
// Unit tests for utility functions
@Test
fun `processNotification should extract correct data from WhatsApp notification`() {
    // Given
    val notification = createMockWhatsAppNotification()
    
    // When
    val result = notificationProcessor.processNotification(notification)
    
    // Then
    assertEquals("WhatsApp", result.appName)
    assertEquals("John Doe", result.contact)
    assertEquals("Hello there!", result.message)
}

// Integration tests for services
@Test
fun `TelegramService should send notification successfully`() = runTest {
    // Given
    val message = "Test monitoring message"
    
    // When
    val result = telegramService.sendMessage(message)
    
    // Then
    assertTrue(result.isSuccess)
}
```

### Testing on Devices

**Required Testing:**
- ✅ **Huawei Nova 3i** (primary target)
- ✅ **Android 8.1** (minimum API level)
- ✅ **Android 14** (latest API level)

**Recommended Testing:**
- Samsung Galaxy devices
- Xiaomi devices
- OnePlus devices
- Stock Android devices

## 📝 Submitting Changes

### Pull Request Process

1. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes**
   - Follow coding standards
   - Add/update tests
   - Update documentation

3. **Test thoroughly**
   - Run unit tests: `./gradlew test`
   - Run integration tests: `./gradlew connectedAndroidTest`
   - Test on real devices

4. **Commit with clear messages**
   ```bash
   git commit -m "feat: add WhatsApp message content extraction"
   git commit -m "fix: resolve battery optimization issue on EMUI 9"
   git commit -m "docs: update installation guide for Android 14"
   ```

5. **Submit pull request**
   - Provide clear description
   - Reference related issues
   - Include testing evidence

### Pull Request Template

```markdown
## Description
Brief description of changes made.

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Performance improvement
- [ ] Security enhancement

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Tested on Huawei Nova 3i
- [ ] Tested on other devices (specify)

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] No security vulnerabilities introduced
```

## 🐛 Issue Guidelines

### Bug Reports

Use the bug report template and include:

- **Device information** (model, Android version, EMUI version)
- **App version** and installation method
- **Steps to reproduce** the issue
- **Expected vs actual behavior**
- **Logs and screenshots** (if applicable)
- **Telegram bot behavior** (if relevant)

### Feature Requests

Use the feature request template and include:

- **Problem description** - what issue does this solve?
- **Proposed solution** - how should it work?
- **Use case examples** - when would this be useful?
- **Device compatibility** - which devices should support this?
- **Privacy considerations** - any privacy implications?

## 🔒 Security Guidelines

### Security-First Development

- **Never log sensitive data** (bot tokens, chat IDs, personal messages)
- **Encrypt all stored data** using Android Keystore
- **Validate all inputs** to prevent injection attacks
- **Use secure communication** (HTTPS/TLS only)
- **Follow Android security best practices**

### Reporting Security Issues

**DO NOT** create public issues for security vulnerabilities. Instead:

1. **Email security report** to: security@example.com
2. **Include details** of the vulnerability
3. **Provide proof of concept** (if safe to do so)
4. **Allow reasonable time** for response and fix

## 📚 Documentation Standards

### Code Documentation

```kotlin
/**
 * Service responsible for monitoring device location and sending updates via Telegram.
 * 
 * This service runs in the background and periodically requests location updates
 * based on the configured interval. It handles both foreground and background
 * location access while respecting battery optimization settings.
 * 
 * @since 1.0.0
 * @see TelegramService for message sending functionality
 */
class LocationService : Service() {
    // Implementation
}
```

### README Updates

When adding features, update:
- Feature descriptions
- Installation instructions
- Configuration examples
- Command references
- Troubleshooting guides

## 🌍 Localization

### Adding Language Support

1. **Create string resources**
   ```xml
   <!-- values-es/strings.xml -->
   <string name="app_name">Monitor de Control Parental</string>
   <string name="setup_title">Configuración del Servicio del Sistema</string>
   ```

2. **Update layouts** to use string resources
   ```xml
   <TextView
       android:text="@string/setup_title"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content" />
   ```

3. **Test with different locales**

### Supported Languages

Currently supported:
- 🇺🇸 English (default)

Help us add support for:
- 🇪🇸 Spanish
- 🇫🇷 French
- 🇩🇪 German
- 🇨🇳 Chinese (Simplified)
- 🇯🇵 Japanese

## 📞 Getting Help

### Community Support

- 💬 [**GitHub Discussions**](https://github.com/yourusername/parental-control-monitor/discussions) - General questions and ideas
- 🐛 [**Issues**](https://github.com/yourusername/parental-control-monitor/issues) - Bug reports and feature requests
- 📧 [**Email**](mailto:support@example.com) - Direct support for contributors

### Development Resources

- 📖 [Android Developer Guide](https://developer.android.com/guide)
- 🤖 [Telegram Bot API](https://core.telegram.org/bots/api)
- 🏗️ [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- 🔒 [Android Security](https://developer.android.com/topic/security)

## 🎉 Recognition

### Contributors

We recognize all contributors in our:
- 📄 **README contributors section**
- 🏷️ **Release notes** for specific contributions
- 💬 **GitHub discussions** shout-outs
- 🎯 **Project milestones** acknowledgments

### Types of Recognition

- **🌟 First-time contributors** - Welcome and feature in README
- **🔧 Bug fixers** - Mentioned in release notes
- **✨ Feature developers** - Featured in major releases
- **📚 Documentation contributors** - Acknowledged in docs
- **🛡️ Security researchers** - Recognition in security advisories

---

Thank you for contributing to making parental monitoring more effective and secure! 🙏

**Questions?** Feel free to reach out via [GitHub Discussions](https://github.com/yourusername/parental-control-monitor/discussions) or [email](mailto:support@example.com).
