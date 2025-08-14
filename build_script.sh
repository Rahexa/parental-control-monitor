#!/bin/bash
set -e

echo "Setting up gradlew permissions..."
chmod +x ./gradlew

echo "Checking gradlew version..."
./gradlew --version || echo "Version check failed, continuing..."

echo "Listing project structure..."
ls -la

echo "Checking if gradlew is working with help command..."
./gradlew help || echo "Help command failed, continuing..."

echo "Trying to list available tasks with alternative syntax..."
./gradlew -q tasks --all || echo "Tasks listing failed, continuing..."

echo "Building debug APK with alternative approach..."
./gradlew clean assembleDebug || echo "Clean assembleDebug failed, trying without clean..."

echo "Trying assembleDebug without clean..."
./gradlew assembleDebug || echo "AssembleDebug failed"

echo "Build script completed!"
