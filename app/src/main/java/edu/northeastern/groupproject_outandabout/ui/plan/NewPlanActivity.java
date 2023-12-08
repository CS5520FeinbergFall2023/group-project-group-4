package edu.northeastern.groupproject_outandabout.ui.plan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Context;
import edu.northeastern.groupproject_outandabout.R;

public class NewPlanActivity extends AppCompatActivity implements LocationListener {

    private ProgressBar locationLoadingWheel;
    private TextView fetchingLocationTV;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationLoadingWheel = findViewById(R.id.locationLoadingWheel);
        fetchingLocationTV = findViewById(R.id.fetchingLocationTV);

        findViewById(R.id.startByCurrentLocationButton).setOnClickListener(v -> {
            checkLocationPermission();
        });

        findViewById(R.id.startByInputLocationButton).setOnClickListener(v -> {
            Intent intent = new Intent(NewPlanActivity.this, InitialPlanActivity.class);
            // Indication that user will input location
            intent.putExtra("inputLocation", true);
            startActivity(intent);
        });
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            retrieveLocation();
        }
    }

    private void retrieveLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Make loading wheel and text visible while waiting for GPS
            locationLoadingWheel.setVisibility(View.VISIBLE);
            fetchingLocationTV.setVisibility(View.VISIBLE);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // Hide loading wheel and text
        locationLoadingWheel.setVisibility(View.INVISIBLE);
        fetchingLocationTV.setVisibility(View.INVISIBLE);

        // Stop updates after getting current location
        locationManager.removeUpdates((LocationListener) this);

        Intent intent = new Intent(NewPlanActivity.this, InitialPlanActivity.class);
        intent.putExtra("latitude", location.getLatitude());
        intent.putExtra("longitude", location.getLongitude());
        startActivity(intent);
    }

    // May need more location listener methods, can be added

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                retrieveLocation();
            } else {
                Toast.makeText(this, "Location permission is required to use this feature!", Toast.LENGTH_LONG).show();
            }
        }
    }
}