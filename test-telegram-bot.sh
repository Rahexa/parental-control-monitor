#!/bin/bash

# Telegram Bot Testing Script

echo "=== Telegram Bot Configuration Helper ==="
echo ""

# Variables to be filled by user
BOT_TOKEN=""
CHAT_ID=""

if [ -z "$BOT_TOKEN" ] || [ -z "$CHAT_ID" ]; then
    echo "❌ Please edit this script and add your bot credentials:"
    echo ""
    echo "1. Create bot with @BotFather on Telegram"
    echo "2. Get bot token from BotFather"
    echo "3. Start conversation with your bot"
    echo "4. Get chat ID from: https://api.telegram.org/bot<TOKEN>/getUpdates"
    echo "5. Update BOT_TOKEN and CHAT_ID variables in this script"
    echo ""
    echo "Current values:"
    echo "BOT_TOKEN='$BOT_TOKEN'"
    echo "CHAT_ID='$CHAT_ID'"
    exit 1
fi

# Test bot connection
echo "🔍 Testing Telegram bot connection..."
RESPONSE=$(curl -s "https://api.telegram.org/bot${BOT_TOKEN}/getMe")

if echo "$RESPONSE" | grep -q '"ok":true'; then
    BOT_USERNAME=$(echo "$RESPONSE" | grep -o '"username":"[^"]*"' | cut -d'"' -f4)
    echo "✅ Bot connection successful!"
    echo "🤖 Bot username: @$BOT_USERNAME"
else
    echo "❌ Bot connection failed!"
    echo "Response: $RESPONSE"
    exit 1
fi

# Test message sending
echo ""
echo "📤 Testing message sending..."
TEST_MESSAGE="🎯 Parental Control Monitor - Bot Test

📱 This is a test message from your monitoring app.
🔧 Bot setup successful!
⏰ Time: $(date)

✅ Ready for deployment!"

SEND_RESPONSE=$(curl -s -X POST "https://api.telegram.org/bot${BOT_TOKEN}/sendMessage" \
    -d "chat_id=${CHAT_ID}" \
    -d "text=${TEST_MESSAGE}" \
    -d "parse_mode=HTML")

if echo "$SEND_RESPONSE" | grep -q '"ok":true'; then
    echo "✅ Test message sent successfully!"
    echo "📱 Check your Telegram for the test message"
else
    echo "❌ Failed to send test message!"
    echo "Response: $SEND_RESPONSE"
    echo ""
    echo "Common issues:"
    echo "- Wrong chat ID"
    echo "- Haven't started conversation with bot"
    echo "- Bot not added to group (if using group chat)"
fi

echo ""
echo "🔧 To use these credentials in your app:"
echo "Edit app/build.gradle and replace:"
echo "TELEGRAM_BOT_TOKEN with: \"$BOT_TOKEN\""
echo "TELEGRAM_CHAT_ID with: \"$CHAT_ID\""
