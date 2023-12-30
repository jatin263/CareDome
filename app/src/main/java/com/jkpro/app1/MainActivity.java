package com.jkpro.app1;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 1;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 2;
    private static final int MY_PERMISSIONS_REQUEST_CALL = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check and request Internet permission
        if (!checkPermission(Manifest.permission.INTERNET)) {
            requestPermission(Manifest.permission.INTERNET, MY_PERMISSIONS_REQUEST_INTERNET);
        } else {
            // Internet permission granted, proceed to the next permission
            checkAndRequestLocationPermission();
        }
    }

    private void checkAndRequestLocationPermission() {
        // Check and request Location permission
        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            // Location permission granted, proceed to the next permission
            checkAndRequestCallPermission();
        }
    }

    private void checkAndRequestCallPermission() {
        // Check and request Call permission
        if (!checkPermission(Manifest.permission.CALL_PHONE)) {
            requestPermission(Manifest.permission.CALL_PHONE, MY_PERMISSIONS_REQUEST_CALL);
        } else {
            // Call permission granted, all permissions obtained
            // Continue with your app's initialization logic here
        }
    }

    private boolean checkPermission(String permission) {
        // Check if the app has the specified permission
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permission, int requestCode) {
        // Request the specified permission
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Check if the permission request is granted
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, proceed based on the requested permission
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_INTERNET:
                    // Internet permission granted, proceed to the next permission
                    checkAndRequestLocationPermission();
                    break;
                case MY_PERMISSIONS_REQUEST_LOCATION:
                    // Location permission granted, proceed to the next permission
                    checkAndRequestCallPermission();
                    break;
                case MY_PERMISSIONS_REQUEST_CALL:
                    // Call permission granted, all permissions obtained
                    // Continue with your app's initialization logic here
                    break;
            }
        } else {
            // Permission denied, handle accordingly (e.g., show a message)
        }
    }
    public void emergencyCall(View view) {
        Intent intent = new Intent(this, emergency_activity.class);
        startActivity(intent);
    }
    public void gotoWeather(View view) {
        Intent intent = new Intent(this, weather_activity.class);
        startActivity(intent);
    }
    public void gotoSafetyTips(View view) {
        Intent intent = new Intent(this, safetytip_activity.class);
        startActivity(intent);
    }

    public void gotoLocationActivity(View view){
        Intent intent = new Intent(this, location_send_activity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

