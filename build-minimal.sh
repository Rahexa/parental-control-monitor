#!/bin/bash
set -e

echo "=== Minimal build approach ==="

# Just try to build without any fancy commands
chmod +x gradlew
echo "Running basic build..."
./gradlew build --no-daemon --console=plain
