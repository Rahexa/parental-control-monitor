#!/bin/bash
set -e

echo "=== Android CI Build Script ==="
echo "Starting build process..."

echo "Checking Gradle wrapper..."
ls -la gradlew
chmod +x gradlew

echo "Testing Gradle version..."
./gradlew --version

echo "Listing available tasks..."
./gradlew tasks --console=plain

echo "Building debug APK..."
./gradlew app:assembleDebug --console=plain

echo "Build completed successfully!"
echo "APK location: app/build/outputs/apk/debug/"
