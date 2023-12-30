package com.jkpro.app1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class weather_activity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        RequestQueue queue = Volley.newRequestQueue(this);

        // Check for and request ACCESS_FINE_LOCATION permission

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                String apiUrl1 = "https://jatinprojectapi.000webhostapp.com/honeyapi/tempApi.php?lat=" + latitude + "&long=" + longitude;
                Uri apiUrl = Uri.parse(apiUrl1);
                System.out.println(apiUrl);
                // Now latitude and longitude contain the device's location coordinates
                // You can use these values as needed
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET, apiUrl1, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Handle the response (e.g., parse JSON or update UI)
                                // Example: Log the response
                                try {
                                    String msg = response.getString("message");
                                    TextView textView = findViewById(R.id.tempData);
                                    textView.setText(msg);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                System.out.println(response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle errors (e.g., show an error message)
                                // Example: Log the error
                                System.out.println("Error: " + error.toString());
                            }
                        }
                );
                queue.add(jsonObjectRequest);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        // Request location updates
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }

    public void gotoBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for and request ACCESS_FINE_LOCATION permission
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Request location updates when the activity starts
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Stop location updates when the activity is no longer visible
        locationManager.removeUpdates(locationListener);
    }

}
