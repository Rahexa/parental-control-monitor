<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

# Parental Control Monitor - Development Guidelines

This is an Android parental control application with Telegram bot integration. When working on this project, please follow these guidelines:

## Project Structure
- **Kotlin**: Primary language for Android development
- **Architecture**: Service-based architecture with background monitoring
- **Dependencies**: Retrofit for API calls, Room for local storage, Work Manager for background tasks

## Key Components
- `MonitoringService`: Main background service for continuous monitoring
- `TelegramService`: Handles Telegram API communication
- `AccessibilityMonitorService`: Real-time app monitoring using Accessibility API
- `FileMonitor`: File system monitoring utility

## Development Principles
1. **Privacy First**: Always ensure data protection and user privacy
2. **Permission Handling**: Properly request and handle Android permissions
3. **Background Processing**: Use appropriate background services and workers
4. **Error Handling**: Implement robust error handling for network and system operations
5. **Legal Compliance**: Ensure all features comply with privacy laws and regulations

## Code Standards
- Use meaningful variable and function names
- Add appropriate comments for complex logic
- Handle edge cases and null safety
- Use coroutines for asynchronous operations
- Follow Android development best practices

## Security Considerations
- Encrypt sensitive data (Telegram tokens, chat IDs)
- Validate all user inputs
- Use secure communication channels
- Implement proper permission checks
- Handle accessibility service data responsibly

## Testing
- Test on different Android versions (API 24+)
- Verify permission flows work correctly
- Test background service persistence
- Validate Telegram integration
- Check accessibility service functionality
