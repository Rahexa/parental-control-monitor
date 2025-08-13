#!/bin/bash
set -e

echo "=== Debugging gradlew file ==="
echo "File exists:"
ls -la gradlew || echo "gradlew not found"

echo "First few bytes of gradlew:"
head -c 50 gradlew | od -c

echo "First few lines of gradlew:"
head -3 gradlew

echo "=== Making gradlew executable ==="
chmod +x gradlew

echo "=== Testing simple gradle command ==="
./gradlew --version
