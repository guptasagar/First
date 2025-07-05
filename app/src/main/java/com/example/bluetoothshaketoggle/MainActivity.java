package com.example.bluetoothshaketoggle;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final float SHAKE_THRESHOLD = 12.0f;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private BluetoothAdapter bluetoothAdapter;
    private Vibrator vibrator;
    
    private TextView statusText;
    private TextView bluetoothStatusText;
    private TextView instructionsText;
    
    private long lastShakeTime = 0;
    private boolean isShakeDetectionActive = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        initializeSensors();
        initializeBluetooth();
        checkPermissions();
        
        updateUI();
    }
    
    private void initializeViews() {
        statusText = findViewById(R.id.statusText);
        bluetoothStatusText = findViewById(R.id.bluetoothStatusText);
        instructionsText = findViewById(R.id.instructionsText);
        
        instructionsText.setText("Shake your phone to toggle Bluetooth!\n\nMake sure to grant all required permissions.");
    }
    
    private void initializeSensors() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        
        if (accelerometer == null) {
            Toast.makeText(this, "Accelerometer not found on this device", Toast.LENGTH_LONG).show();
        }
    }
    
    private void initializeBluetooth() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported on this device", Toast.LENGTH_LONG).show();
        }
    }
    
    private void checkPermissions() {
        String[] permissions;
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions = new String[]{
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.ACCESS_FINE_LOCATION
            };
        } else {
            permissions = new String[]{
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION
            };
        }
        
        boolean allPermissionsGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false;
                break;
            }
        }
        
        if (!allPermissionsGranted) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        } else {
            startShakeDetection();
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            
            if (allPermissionsGranted) {
                startShakeDetection();
                Toast.makeText(this, "Permissions granted! Shake detection is now active.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions required for app to function properly", Toast.LENGTH_LONG).show();
            }
        }
    }
    
    private void startShakeDetection() {
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            isShakeDetectionActive = true;
            statusText.setText("Shake detection: ACTIVE");
            statusText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
    }
    
    private void stopShakeDetection() {
        sensorManager.unregisterListener(this);
        isShakeDetectionActive = false;
        statusText.setText("Shake detection: INACTIVE");
        statusText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            
            float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;
            
            if (acceleration > SHAKE_THRESHOLD) {
                long currentTime = System.currentTimeMillis();
                
                // Prevent multiple toggles in quick succession
                if (currentTime - lastShakeTime > SHAKE_SLOP_TIME_MS) {
                    lastShakeTime = currentTime;
                    onShakeDetected();
                }
            }
        }
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
    
    private void onShakeDetected() {
        // Provide haptic feedback
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(200);
            }
        }
        
        // Toggle Bluetooth
        toggleBluetooth();
        
        // Update UI
        updateUI();
    }
    
    private void toggleBluetooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_SHORT).show();
            return;
        }
        
        try {
            if (bluetoothAdapter.isEnabled()) {
                // Turn off Bluetooth
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) 
                            == PackageManager.PERMISSION_GRANTED) {
                        bluetoothAdapter.disable();
                        Toast.makeText(this, "Bluetooth turned OFF", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    bluetoothAdapter.disable();
                    Toast.makeText(this, "Bluetooth turned OFF", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Turn on Bluetooth
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) 
                            == PackageManager.PERMISSION_GRANTED) {
                        bluetoothAdapter.enable();
                        Toast.makeText(this, "Bluetooth turned ON", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    bluetoothAdapter.enable();
                    Toast.makeText(this, "Bluetooth turned ON", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (SecurityException e) {
            Toast.makeText(this, "Permission denied for Bluetooth control", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void updateUI() {
        if (bluetoothAdapter != null) {
            try {
                boolean isBluetoothEnabled = bluetoothAdapter.isEnabled();
                bluetoothStatusText.setText("Bluetooth: " + (isBluetoothEnabled ? "ON" : "OFF"));
                bluetoothStatusText.setTextColor(getResources().getColor(
                    isBluetoothEnabled ? android.R.color.holo_blue_bright : android.R.color.holo_red_dark));
            } catch (SecurityException e) {
                bluetoothStatusText.setText("Bluetooth: Permission required");
                bluetoothStatusText.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            }
        } else {
            bluetoothStatusText.setText("Bluetooth: Not supported");
            bluetoothStatusText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (isShakeDetectionActive && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
        updateUI();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (isShakeDetectionActive) {
            sensorManager.unregisterListener(this);
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopShakeDetection();
    }
}