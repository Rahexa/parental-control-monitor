#!/bin/bash
set -e

echo "Making gradlew executable..."
chmod +x gradlew

echo "Listing projects..."
./gradlew projects

echo "Building project..."
./gradlew build
