#!/bin/bash

# Parental Control Monitor - Debug Testing Script

echo "=== Parental Control Monitor Testing Tools ==="

# Install APK
install_apk() {
    echo "Installing debug APK..."
    adb install -r parental-control-debug.apk
    echo "APK installed successfully!"
}

# Check if app is running
check_services() {
    echo "Checking running services..."
    adb shell "ps | grep parentalcontrol"
    echo ""
    echo "Checking if accessibility service is enabled..."
    adb shell "settings get secure enabled_accessibility_services"
}

# View app logs
view_logs() {
    echo "Viewing app logs (Ctrl+C to stop)..."
    adb logcat | grep -i "parentalcontrol\|telegram\|monitoring"
}

# Check app permissions
check_permissions() {
    echo "Checking app permissions..."
    adb shell "pm list permissions -f com.parentalcontrol.monitor"
    echo ""
    echo "Checking granted permissions..."
    adb shell "dumpsys package com.parentalcontrol.monitor | grep permission"
}

# Check database
check_database() {
    echo "Checking app database..."
    adb shell "ls -la /data/data/com.parentalcontrol.monitor/databases/"
    echo ""
    echo "Database content (if accessible)..."
    adb shell "sqlite3 /data/data/com.parentalcontrol.monitor/databases/monitoring_database 'SELECT name FROM sqlite_master WHERE type=\"table\";'" 2>/dev/null || echo "Database not accessible (requires root)"
}

# Launch app
launch_app() {
    echo "Launching app..."
    adb shell "am start -n com.parentalcontrol.monitor/.AutoSetupActivity"
}

# Stop app
stop_app() {
    echo "Stopping app..."
    adb shell "am force-stop com.parentalcontrol.monitor"
}

# Clear app data
clear_data() {
    echo "Clearing app data..."
    adb shell "pm clear com.parentalcontrol.monitor"
    echo "App data cleared!"
}

# Uninstall app
uninstall_app() {
    echo "Uninstalling app..."
    adb shell "pm uninstall com.parentalcontrol.monitor"
    echo "App uninstalled!"
}

# Menu
case "$1" in
    "install")
        install_apk
        ;;
    "check")
        check_services
        ;;
    "logs")
        view_logs
        ;;
    "permissions")
        check_permissions
        ;;
    "database")
        check_database
        ;;
    "launch")
        launch_app
        ;;
    "stop")
        stop_app
        ;;
    "clear")
        clear_data
        ;;
    "uninstall")
        uninstall_app
        ;;
    *)
        echo "Usage: $0 {install|check|logs|permissions|database|launch|stop|clear|uninstall}"
        echo ""
        echo "Commands:"
        echo "  install     - Install the debug APK"
        echo "  check       - Check if services are running"
        echo "  logs        - View real-time app logs"
        echo "  permissions - Check app permissions"
        echo "  database    - Check app database"
        echo "  launch      - Launch the app"
        echo "  stop        - Stop the app"
        echo "  clear       - Clear app data"
        echo "  uninstall   - Uninstall the app"
        ;;
esac
