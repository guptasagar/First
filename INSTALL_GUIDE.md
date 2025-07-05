# 📱 Bluetooth Shake Toggle - Installation Guide

## 🚀 Quick Start

### Method 1: Android Studio (Easiest)
1. **Download Android Studio**: https://developer.android.com/studio
2. **Open Project**: File → Open → Select the `BluetoothShakeToggle` folder
3. **Wait for Sync**: Android Studio will automatically sync the project
4. **Build APK**: Build → Build Bundle(s) / APK(s) → Build APK(s)
5. **Find APK**: `app/build/outputs/apk/debug/app-debug.apk`

### Method 2: Command Line
```bash
# Navigate to project folder
cd BluetoothShakeToggle

# Run build script
./build_apk.sh

# Or manually:
./gradlew assembleDebug
```

## 📲 Installing the APK

### Option A: Direct Install (USB Debugging)
1. **Enable Developer Options** on your phone:
   - Settings → About Phone → Tap "Build Number" 7 times
2. **Enable USB Debugging**:
   - Settings → Developer Options → USB Debugging
3. **Connect Phone** via USB
4. **Install APK**:
   ```bash
   adb install app-debug.apk
   ```

### Option B: Side-load Installation
1. **Copy APK** to your phone (via USB, email, cloud storage)
2. **Enable Unknown Sources**:
   - Settings → Security → Unknown Sources (or Install unknown apps)
3. **Install APK**:
   - Use a file manager to find the APK
   - Tap the APK file
   - Follow installation prompts

## 🎯 First Time Setup

### 1. Launch the App
- Find "Bluetooth Shake Toggle" in your app drawer
- Tap to open

### 2. Grant Permissions
The app will request these permissions (all required):
- ✅ **Bluetooth** - To control Bluetooth
- ✅ **Location** - Required for Bluetooth on Android 6.0+
- ✅ **Vibration** - For haptic feedback

### 3. Start Using
- **Shake Detection**: Should show "ACTIVE" in green
- **Bluetooth Status**: Shows current ON/OFF state
- **Test It**: Shake your phone to toggle Bluetooth!

## 🔧 Troubleshooting

### App Won't Install
- **Solution**: Enable "Install unknown apps" in Settings
- **Android 8.0+**: Settings → Apps → Special Access → Install unknown apps

### Permissions Not Working
- **Solution**: Go to Settings → Apps → Bluetooth Shake Toggle → Permissions
- **Enable All**: Make sure all permissions are granted

### Shake Not Detected
- **Try**: Shake with more intensity
- **Check**: Ensure app is in foreground
- **Verify**: Permissions are granted

### Bluetooth Not Toggling
- **Android 12+**: Make sure "Nearby devices" permission is granted
- **Check**: Bluetooth hardware is supported
- **Restart**: Close and reopen the app

## 📋 System Requirements

- **Android Version**: 6.0 (API 23) or higher
- **Hardware**: Accelerometer (standard on all phones)
- **Features**: Bluetooth support
- **Permissions**: Location, Bluetooth, Vibration

## 🎉 Usage Tips

### Shake Technique
- **Intensity**: Medium to strong shake
- **Duration**: Quick 0.5-1 second shake
- **Direction**: Any direction works
- **Frequency**: Wait 0.5 seconds between shakes

### Best Practices
- Keep app open for shake detection
- Grant all permissions for full functionality
- Shake firmly but don't overdo it
- Check Bluetooth status in the app

## 📞 Support

If you encounter issues:
1. Check this troubleshooting guide
2. Verify all permissions are granted
3. Try restarting the app
4. Ensure your device meets system requirements

---

**Enjoy your new Bluetooth shake toggle functionality!** 🎉