# Telegram Bot Setup Guide for Parental Control Monitor

## Step 1: Create Telegram Bot

1. **Open Telegram** and search for `@BotFather`
2. **Start conversation** with BotFather
3. **Send command**: `/newbot`
4. **Choose bot name**: e.g., "Parental Control Monitor Bot"
5. **Choose username**: e.g., "parental_control_monitor_bot" (must end with 'bot')

## Step 2: Get Bot Token

After creating the bot, BotFather will give you:
- **Bot Token**: `123456789:ABCdefGhIJKlmNoPQRsTuVwXyZ` (example)
- **Bot Username**: `@parental_control_monitor_bot`

⚠️ **IMPORTANT**: Save this token securely - you'll need it for configuration!

## Step 3: Get Chat ID

### Method 1: Direct Message (Recommended for personal use)
1. **Start conversation** with your bot: `@parental_control_monitor_bot`
2. **Send any message** to the bot (e.g., "Hello")
3. **Get Chat ID** using this URL in browser:
   ```
   https://api.telegram.org/bot<YOUR_BOT_TOKEN>/getUpdates
   ```
   Replace `<YOUR_BOT_TOKEN>` with your actual token
4. **Find Chat ID** in the response JSON: `"chat":{"id":123456789}`

### Method 2: Group Chat (For family monitoring)
1. **Create group** or use existing family group
2. **Add your bot** to the group as admin
3. **Send message** in the group mentioning the bot
4. **Get Chat ID** using the same URL method above
5. **Group Chat ID** will be negative: `-123456789`

## Step 4: Test Bot Manually

Test if your bot works by sending a message via URL:
```
https://api.telegram.org/bot<YOUR_BOT_TOKEN>/sendMessage?chat_id=<CHAT_ID>&text=Test message
```

If successful, you'll see the message in Telegram!

## Configuration Values Needed:

- **TELEGRAM_BOT_TOKEN**: `123456789:ABCdefGhIJKlmNoPQRsTuVwXyZ`
- **TELEGRAM_CHAT_ID**: `123456789` (or `-123456789` for groups)

## Security Notes:

- ⚠️ Never share your bot token publicly
- 🔒 Keep chat ID private
- 🛡️ The bot can only send messages to chats that have interacted with it first
- 📱 For family monitoring, add bot to private family group
