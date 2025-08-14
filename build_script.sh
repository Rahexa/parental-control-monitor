#!/bin/bash
set -e

echo "Setting up gradlew permissions..."
chmod +x ./gradlew

echo "Testing simple gradle command..."
./gradlew --version || echo "Gradlew version failed, trying alternative..."

echo "Using gradle wrapper with explicit wrapper task..."
./gradlew wrapper --gradle-version 8.2 || echo "Wrapper task failed, continuing..."

echo "Building debug APK with gradle command..."
gradle assembleDebug || echo "Direct gradle failed, trying gradlew..."

echo "Building debug APK with gradlew..."
./gradlew assembleDebug || echo "Gradlew assembleDebug failed"

echo "Building release APK..."
./gradlew assembleRelease || gradle assembleRelease || echo "Release build failed"

echo "Listing build outputs..."
find . -name "*.apk" -type f

echo "Build script completed!"
