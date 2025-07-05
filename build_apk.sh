#!/bin/bash

echo "🚀 Building Bluetooth Shake Toggle APK..."
echo "==============================================="

# Check if Android SDK is available
if [ -z "$ANDROID_HOME" ]; then
    echo "⚠️  ANDROID_HOME not set. Please install Android SDK first."
    echo "   Download from: https://developer.android.com/studio"
    exit 1
fi

# Make gradlew executable
chmod +x gradlew

# Clean previous builds
echo "🧹 Cleaning previous builds..."
./gradlew clean

# Build debug APK
echo "🔨 Building debug APK..."
./gradlew assembleDebug

# Check if build succeeded
if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "📱 APK location: app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    echo "🎉 Your Bluetooth Shake Toggle app is ready!"
    echo "   Install it on your phone and start shaking to toggle Bluetooth!"
else
    echo "❌ Build failed. Please check the errors above."
    echo "   Make sure you have Android SDK installed and ANDROID_HOME set."
fi