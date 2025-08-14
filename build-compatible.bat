@echo off
echo Building with JDK compatibility mode...

REM Set environment variables for JDK compatibility
set GRADLE_OPTS=-Dorg.gradle.jvmargs="-Xmx4g -XX:+UseG1GC" ^
-Dkotlin.compiler.execution.strategy=in-process ^
-Dorg.gradle.unsafe.configuration-cache=false ^
-Dandroid.experimental.enableNewResourceShrinker=false

echo Cleaning previous build...
gradlew.bat clean --no-daemon

echo Building APK with compatibility settings...
gradlew.bat assembleDebug --no-daemon --stacktrace ^
-Pandroid.experimental.enableNewResourceShrinker=false ^
-Pandroid.enableR8.fullMode=false

if %ERRORLEVEL% EQU 0 (
    echo BUILD SUCCESSFUL!
    echo APK location: app\build\outputs\apk\debug\
    dir "app\build\outputs\apk\debug\*.apk" 2>nul
) else (
    echo BUILD FAILED - Check GitHub Actions for successful build
    echo Visit: https://github.com/Rahexa/parental-control-monitor/actions
)

pause
