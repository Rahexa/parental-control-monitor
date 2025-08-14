#!/bin/bash
set -e

echo "Setting up gradlew permissions..."
chmod +x ./gradlew

echo "Building debug APK..."
./gradlew assembleDebug

echo "Building release APK..."
./gradlew assembleRelease

echo "Listing build outputs..."
find . -name "*.apk" -type f

echo "Build completed successfully!"
