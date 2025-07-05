# Bluetooth Shake Toggle - Android App

A simple and intuitive Android app that allows you to toggle Bluetooth on/off by shaking your phone.

## Features

- **Shake Detection**: Uses the device's accelerometer to detect shake gestures
- **Bluetooth Control**: Toggle Bluetooth on/off with a simple shake
- **Haptic Feedback**: Vibration feedback when shake is detected
- **Smart Debouncing**: Prevents accidental multiple toggles
- **Permission Handling**: Automatically requests required permissions
- **Modern UI**: Clean, Material Design interface
- **Real-time Status**: Shows current Bluetooth and shake detection status

## How it Works

1. **Shake Detection**: The app uses the device's accelerometer to detect shake gestures
2. **Threshold-based**: Shakes above a certain acceleration threshold trigger the toggle
3. **Debouncing**: Prevents multiple toggles in quick succession (500ms cooldown)
4. **Feedback**: Provides haptic feedback and visual confirmation

## Requirements

- Android 6.0 (API level 23) or higher
- Device with accelerometer (standard on all modern smartphones)
- Bluetooth hardware support

## Permissions

The app requires the following permissions:

### Bluetooth Permissions:
- `BLUETOOTH` - Basic Bluetooth access
- `BLUETOOTH_ADMIN` - Bluetooth control (enable/disable)
- `BLUETOOTH_CONNECT` - Android 12+ Bluetooth connection
- `BLUETOOTH_ADVERTISE` - Android 12+ Bluetooth advertising

### Location Permissions:
- `ACCESS_FINE_LOCATION` - Required for Bluetooth functionality on Android 6.0+

### Other Permissions:
- `VIBRATE` - Haptic feedback when shake is detected

## Installation

### Option 1: Build from Source

1. Clone this repository
2. Open in Android Studio
3. Build and run on your device

### Option 2: Direct Installation

1. Build the APK using Android Studio
2. Install on your device with `adb install`

## Usage

1. **Launch the app**
2. **Grant permissions** when prompted
3. **Start shaking** your phone to toggle Bluetooth
4. **Check status** on the main screen

### Shake Technique

- **Intensity**: Moderate to strong shaking works best
- **Duration**: A quick shake (0.5-1 second) is sufficient
- **Direction**: Any direction works - up/down, left/right, or circular

## Technical Details

### Shake Detection Algorithm

```java
float acceleration = Math.sqrt(x² + y² + z²) - GRAVITY_EARTH;
if (acceleration > SHAKE_THRESHOLD) {
    // Trigger Bluetooth toggle
}
```

- **Threshold**: 12.0 m/s² (configurable)
- **Debounce**: 500ms between toggles
- **Sensor Rate**: UI refresh rate for responsive detection

### Bluetooth Control

The app handles different Android versions:

- **Android 6.0-11**: Uses `BluetoothAdapter.enable()/disable()`
- **Android 12+**: Uses new permission model with `BLUETOOTH_CONNECT`

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/bluetoothshaketoggle/
│   │   └── MainActivity.java          # Main activity with shake detection
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml      # Main UI layout
│   │   ├── values/
│   │   │   ├── colors.xml             # Color definitions
│   │   │   ├── strings.xml            # String resources
│   │   │   └── themes.xml             # App themes
│   │   ├── drawable/                  # UI drawables
│   │   └── xml/                       # Backup rules
│   └── AndroidManifest.xml            # App manifest with permissions
└── build.gradle                       # App build configuration
```

## Customization

### Adjust Shake Sensitivity

In `MainActivity.java`, modify the `SHAKE_THRESHOLD` constant:

```java
private static final float SHAKE_THRESHOLD = 12.0f; // Increase for less sensitivity
```

### Change Debounce Time

Modify the `SHAKE_SLOP_TIME_MS` constant:

```java
private static final int SHAKE_SLOP_TIME_MS = 500; // Milliseconds between toggles
```

## Troubleshooting

### App Not Detecting Shakes
- Check if accelerometer permissions are granted
- Try shaking with more intensity
- Ensure the app is in the foreground

### Bluetooth Not Toggling
- Verify Bluetooth permissions are granted
- Check if device supports Bluetooth
- For Android 12+, ensure `BLUETOOTH_CONNECT` permission is granted

### Permission Issues
- Go to Settings > Apps > Bluetooth Shake Toggle > Permissions
- Enable all required permissions
- Restart the app

## Future Enhancements

- [ ] Background operation (service)
- [ ] Customizable shake sensitivity
- [ ] Sound feedback options
- [ ] Widget support
- [ ] Gesture alternatives (double-tap, etc.)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## License

This project is open source and available under the [MIT License](LICENSE).

## Support

For issues or questions:
- Create an issue in the GitHub repository
- Check the troubleshooting section above

---

**Note**: This app is designed for convenience and accessibility. Please ensure you comply with your device's power management settings and any organizational policies regarding Bluetooth usage.