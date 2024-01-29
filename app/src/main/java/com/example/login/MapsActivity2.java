package com.example.login;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Interpolator;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Location");
        ValueEventListener listener=databaseReference.addValueEventListener(new ValueEventListener() {
            Marker marker=null;
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String databaseLatitudeString = dataSnapshot.child("latitude").getValue().toString().substring(1, dataSnapshot.child("latitude").getValue().toString().length()-1);
                    String databaseLongitudedeString = dataSnapshot.child("longitude").getValue().toString().substring(1, dataSnapshot.child("longitude").getValue().toString().length()-1);
                    String[] stringLat = databaseLatitudeString.split(", ");
                    Arrays.sort(stringLat);
                    String latitude = stringLat[stringLat.length-1].split("=")[1];
                    String[] stringLong = databaseLongitudedeString.split(", ");
                    Arrays.sort(stringLong);
                    String longitude = stringLong[stringLong.length-1].split("=")[1];
                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    if(marker==null) {
                        MarkerOptions a = new MarkerOptions().position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
                        marker = mMap.addMarker(a);
                    }
                    else {
                        marker.setPosition(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
                    }
                    animateMarker(marker,new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(4f));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            private void animateMarker(final Marker marker, final LatLng latLng) {
                final Handler handler = new Handler();
                final long start = SystemClock.uptimeMillis();
                final long duration = 2000;

                final LinearInterpolator interpolator = new LinearInterpolator();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        long elapsed = SystemClock.uptimeMillis() - start;
                        float t = interpolator.getInterpolation((float) elapsed / duration);
                        double lng = t * latLng.longitude + (1 - t) * marker.getPosition().longitude;
                        double lat = t * latLng.latitude + (1 - t) * marker.getPosition().latitude;
                        marker.setPosition(new LatLng(lat, lng));
                        if (t < 1.0) {
                            // Post again 16ms later.
                            handler.postDelayed(this, 8);
                        }
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        // Add a marker in Sydney and move the camera

    }
}