#!/bin/bash
set -e

echo "Making gradlew executable..."
chmod +x gradlew

echo "Building project..."
./gradlew build
