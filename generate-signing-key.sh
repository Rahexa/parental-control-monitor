#!/bin/bash

echo "=== Android APK Signing Key Generator ==="
echo ""

# Check if keytool is available
if ! command -v keytool &> /dev/null; then
    echo "❌ keytool not found! Please install Java JDK."
    echo "Download from: https://adoptium.net/"
    exit 1
fi

echo "This script will generate a signing key for your Android app."
echo ""

# Get user input
read -p "Enter key alias (e.g., 'release'): " KEY_ALIAS
read -p "Enter your name: " USER_NAME
read -p "Enter your organization: " ORG_NAME
read -p "Enter your city: " CITY
read -p "Enter your country code (e.g., US): " COUNTRY

echo ""
echo "Generating keystore..."

# Generate keystore
keytool -genkey -v \
    -keystore release-key.keystore \
    -alias "$KEY_ALIAS" \
    -keyalg RSA \
    -keysize 2048 \
    -validity 10000 \
    -dname "CN=$USER_NAME, O=$ORG_NAME, L=$CITY, C=$COUNTRY"

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Keystore generated successfully!"
    echo ""
    echo "Converting to base64 for GitHub secrets..."
    
    # Convert to base64
    if command -v base64 &> /dev/null; then
        base64 release-key.keystore > keystore.base64
        echo "✅ Base64 file created: keystore.base64"
    else
        echo "⚠️  base64 command not found. Please manually convert the keystore to base64."
    fi
    
    echo ""
    echo "📋 GitHub Secrets Setup:"
    echo "1. Go to your GitHub repository"
    echo "2. Settings → Secrets and variables → Actions"
    echo "3. Add these secrets:"
    echo ""
    echo "   SIGNING_KEY = $(cat keystore.base64 2>/dev/null || echo 'content of keystore.base64 file')"
    echo "   KEY_ALIAS = $KEY_ALIAS"
    echo "   KEY_STORE_PASSWORD = [the password you just entered]"
    echo "   KEY_PASSWORD = [the password you just entered]"
    echo ""
    echo "🔒 Keep your keystore file safe! You'll need it to sign future updates."
    echo ""
    echo "Files created:"
    echo "- release-key.keystore (keep this safe!)"
    echo "- keystore.base64 (copy content to GitHub secrets)"
    
else
    echo "❌ Failed to generate keystore"
    exit 1
fi
