@echo off
echo === Android APK Signing Key Generator ===
echo.

where keytool >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: keytool not found! Please install Java JDK.
    echo Download from: https://adoptium.net/
    pause
    exit /b 1
)

echo This script will generate a signing key for your Android app.
echo.

set /p KEY_ALIAS="Enter key alias (e.g., 'release'): "
set /p USER_NAME="Enter your name: "
set /p ORG_NAME="Enter your organization: "
set /p CITY="Enter your city: "
set /p COUNTRY="Enter your country code (e.g., US): "

echo.
echo Generating keystore...

keytool -genkey -v -keystore release-key.keystore -alias "%KEY_ALIAS%" -keyalg RSA -keysize 2048 -validity 10000 -dname "CN=%USER_NAME%, O=%ORG_NAME%, L=%CITY%, C=%COUNTRY%"

if %errorlevel% equ 0 (
    echo.
    echo ✅ Keystore generated successfully!
    echo.
    echo Converting to base64 for GitHub secrets...
    
    powershell -command "[convert]::ToBase64String((Get-Content -path 'release-key.keystore' -Encoding byte))" > keystore.base64
    
    echo ✅ Base64 file created: keystore.base64
    echo.
    echo 📋 GitHub Secrets Setup:
    echo 1. Go to your GitHub repository
    echo 2. Settings → Secrets and variables → Actions
    echo 3. Add these secrets:
    echo.
    echo    SIGNING_KEY = [content of keystore.base64 file]
    echo    KEY_ALIAS = %KEY_ALIAS%
    echo    KEY_STORE_PASSWORD = [the password you just entered]
    echo    KEY_PASSWORD = [the password you just entered]
    echo.
    echo 🔒 Keep your keystore file safe! You'll need it to sign future updates.
    echo.
    echo Files created:
    echo - release-key.keystore (keep this safe!)
    echo - keystore.base64 (copy content to GitHub secrets)
) else (
    echo ❌ Failed to generate keystore
)

echo.
pause
