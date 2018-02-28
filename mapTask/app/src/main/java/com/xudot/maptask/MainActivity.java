package com.xudot.maptask;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener {

    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    private Marker marker;

    private void addCaptainMarker(LatLng latLng) {
        double lat = latLng.latitude;
        double lon = latLng.longitude;
        if (marker != null) {
            marker.remove();
        }
        if (lat == 0 && lon == 0) {
            if (marker != null)
                marker.remove();
        } else {
            MarkerOptions options = new MarkerOptions();
            LatLng currentLatLng = new LatLng(lat, lon);
            options.position(currentLatLng);
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).draggable(true);
            marker = googleMap.addMarker(options);
//            long atTime = System.currentTimeMillis();
//            marker = DateFormat.getTimeInstance().format(new Date(atTime));
            marker.setTitle("Captain Location");
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,
                    13));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SupportPlaceAutocompleteFragment autocompleteFragment = new SupportPlaceAutocompleteFragment();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        //        autocompleteFragment.setHint("Search Mall Location");
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.place_autocomplete_fragment_mall, autocompleteFragment);
        ft.commit();

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                addCaptainMarker(place.getLatLng());
//                tv_coordinates.setText(place.getName() + "" + place.getAddress());
//                tv_coordinates.setText(place.getViewport().northeast.toString());

//                tv_coordinates.setText(place.getLatLng().latitude + "," + place.getLatLng().longitude);
//                mallName.setText(place.getName());
//
//                areaName.setText(getLocationName(place.getLatLng()));
//                areaDetails.setText(place.getAddress());
//                Log.e("data", place.getViewport().northeast.latitude + "-" + place.getViewport().northeast.longitude + "\n" + place.getViewport().southwest.latitude + "-" + place.getViewport().southwest.longitude);
//                Log.e("data", place.getLatLng().latitude + "," + place.getLatLng().longitude);
            }

            @Override
            public void onError(Status status) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setOnMarkerDragListener(this);
        googleMap.setOnMapLongClickListener(this);

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.4233438, -122.0728817))
                .title("LinkedIn")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).draggable(true));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
//        addCaptainMarker(marker.getPosition());
    }

    @Override
    public void onMarkerDrag(Marker marker) {
//        addCaptainMarker(marker.getPosition());

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        addCaptainMarker(marker.getPosition());

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }
}
