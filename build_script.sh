#!/bin/bash
set -e

echo "Setting up gradlew permissions..."
chmod +x ./gradlew

echo "Running gradlew tasks..."
./gradlew tasks || echo "Tasks command failed, continuing..."

echo "Building debug APK..."
./gradlew assembleDebug

echo "Building release APK..."
./gradlew assembleRelease

echo "Build script completed successfully!"
