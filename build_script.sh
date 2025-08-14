#!/bin/bash
set -e

echo "Setting up gradlew permissions..."
chmod +x ./gradlew

echo "Building debug APK..."
./gradlew assembleDebug

echo "Building release APK..."
./gradlew assembleRelease

echo "Listing all build outputs with details..."
find . -name "*.apk" -type f -exec ls -la {} \;

echo "Checking expected output directories..."
ls -la app/build/outputs/ || echo "outputs directory not found"
ls -la app/build/outputs/apk/ || echo "apk directory not found"

echo "Finding all directories under build/outputs..."
find app/build/outputs -type d || echo "No build outputs found"

echo "Build completed successfully!"
