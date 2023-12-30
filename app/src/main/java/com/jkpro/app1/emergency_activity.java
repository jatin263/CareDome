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

public class emergency_activity extends AppCompatActivity {
    private static final int REQUEST_CALL_PERMISSION = 1;
    private static String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_activity);

        findViewById(R.id.emergencyCallBtnToN1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("9314340872");
            }
        });
        findViewById(R.id.emergencyCallBtnToN2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("8209869372");
            }
        });
        findViewById(R.id.emergencyCallBtnToAmbu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("102");
            }
        });
        findViewById(R.id.emergencyCallBtnToChild).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("1098");
            }
        });
        findViewById(R.id.emergencyCallBtnToPolice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("100");
            }
        });
        findViewById(R.id.emergencyCallBtnToEmer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("112");
            }
        });
        findViewById(R.id.emergencyCallBtnToFire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("101");
            }
        });
        findViewById(R.id.emergencyCallBtnToRescue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("108");
            }
        });
    }

    private void makePhoneCall(String num) {
        this.num = num;
        String phoneNumber = "tel:" + num; // Replace with the actual phone number
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
            startActivity(callIntent);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String num = this.num;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(num);
            } else {
                showToast("Permission denied. Unable to make a call.");
            }
        }
    }



    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void gotoBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
