@echo off
echo Setting up build environment...

REM Set JAVA_HOME to Android Studio's JDK
set "JAVA_HOME=C:\Program Files\Android\Android Studio\jbr"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Java version:
java -version

echo Building APK...
gradlew.bat clean assembleDebug

if %ERRORLEVEL% EQU 0 (
    echo.
    echo BUILD SUCCESSFUL!
    echo.
    echo APK location:
    dir "app\build\outputs\apk\debug\*.apk" 2>nul
    if %ERRORLEVEL% EQU 0 (
        echo APK files found:
        for %%f in ("app\build\outputs\apk\debug\*.apk") do echo   %%f
    ) else (
        echo No APK files found. Checking entire build directory...
        dir "app\build" /s /b *.apk 2>nul
    )
) else (
    echo BUILD FAILED!
    echo Error code: %ERRORLEVEL%
)

pause
