#!/bin/bash
set -e

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

echo "Building project:"
gradle build
