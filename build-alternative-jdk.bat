@echo off
echo Attempting to build with specific JDK configurations...

REM Try with minimal JDK image transformation
echo Trying build with JDK image transformation disabled...
set GRADLE_OPTS=-Dorg.gradle.jvmargs="-Xmx4g -XX:+UseParallelGC" -Dkotlin.compiler.execution.strategy=in-process

gradlew.bat clean assembleDebug --no-daemon --stacktrace -Dorg.gradle.java.home="C:\Program Files\Android\Android Studio\jbr"

if %ERRORLEVEL% NEQ 0 (
    echo Build failed with Android Studio JDK, trying without JDK image...
    gradlew.bat clean assembleDebug --no-daemon --stacktrace -Pandroid.experimental.enableNewResourceShrinker=false
)

pause
