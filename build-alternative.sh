#!/bin/bash
# Don't use set -e so we can see errors and continue

echo "=== Alternative approach: Download Gradle directly ==="

# Download Gradle directly if gradlew fails
GRADLE_VERSION=8.2
GRADLE_HOME=/opt/gradle
GRADLE_URL=https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip

if [ ! -d "$GRADLE_HOME" ]; then
    echo "Downloading Gradle ${GRADLE_VERSION}..."
    wget -q ${GRADLE_URL} -O gradle.zip
    unzip -q gradle.zip
    sudo mv gradle-${GRADLE_VERSION} ${GRADLE_HOME}
    rm gradle.zip
fi

export PATH=${GRADLE_HOME}/bin:$PATH

echo "Using Gradle version:"
gradle --version

echo "Listing projects:"
gradle projects

echo "=== Attempting to build debug APK ==="
gradle :app:assembleDebug --stacktrace --info
DEBUG_EXIT_CODE=$?

if [ $DEBUG_EXIT_CODE -eq 0 ]; then
    echo "=== Debug build successful! ==="
else
    echo "=== Debug build failed with exit code $DEBUG_EXIT_CODE ==="
fi

echo "=== Attempting to build release APK ==="
gradle :app:assembleRelease --stacktrace --info
RELEASE_EXIT_CODE=$?

if [ $RELEASE_EXIT_CODE -eq 0 ]; then
    echo "=== Release build successful! ==="
else
    echo "=== Release build failed with exit code $RELEASE_EXIT_CODE ==="
fi

echo "=== Build summary ==="
echo "Debug build exit code: $DEBUG_EXIT_CODE"
echo "Release build exit code: $RELEASE_EXIT_CODE"

# List any generated APKs
echo "Looking for generated APKs..."
find app/build/outputs/apk -name "*.apk" -type f 2>/dev/null || echo "No APKs found"

# Exit with error if both builds failed
if [ $DEBUG_EXIT_CODE -ne 0 ] && [ $RELEASE_EXIT_CODE -ne 0 ]; then
    echo "Both builds failed!"
    exit 1
fi
