package com.example.tim_pc.projectlasso;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.location.LocationServices;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.content.Context;
import com.google.android.gms.location.FusedLocationProviderApi;



import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import android.view.View;
import android.os.Bundle;

//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
public class MapsActivity extends FragmentActivity implements
        OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {


    //----------------------------------------------------------------------------------------------
    // Global Variables
    //----------------------------------------------------------------------------------------------
    private GoogleMap mMap;
    private UiSettings mUiSettings;

    //entry point to google play services -- currently not used
    //protected GoogleApiClient mGoogleApiClient;
    //private GoogleApiClient client;
    //----------------------------------------------------------------------------------------------
    //All from MyLocationDemoActivity.java

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private Location mLastLocation;

    //==============================================================================================





    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */




    //----------------------------------------------------------------------------------------------
    // This is all boilerplate
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }
    //==============================================================================================

    //------------------------------------------------------------------------------------------
    // Added from retrieve location verbatim,
    // Accesses google play services
    /*
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();

        initListeners();
    }
    */

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();

        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);

        enableMyLocation();



        //==========================================================================================
        // This is all preset sample code for the Map.
        // It adds five markers at Emory and moves the camera to center
        //==========================================================================================
        LatLng emoryMathCS = new LatLng(33.790235, -84.326577);
        mMap.addMarker(new MarkerOptions().position(emoryMathCS).title("Tim").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(emoryMathCS));

        LatLng libs = new LatLng(33.790484, -84.322963);
        mMap.addMarker(new MarkerOptions().position(libs).title("Rachel").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(libs));

        LatLng psych = new LatLng(33.790858, -84.327383);
        mMap.addMarker(new MarkerOptions().position(psych).title("Will").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(psych));

        LatLng duc = new LatLng(33.793595, -84.323724);
        mMap.addMarker(new MarkerOptions().position(duc).title("Amanda").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(duc));

        LatLng gym = new LatLng(33.793427, -84.325682);
        mMap.addMarker(new MarkerOptions().position(gym).title("Danny").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gym));
        //==========================================================================================

    }

    //----------------------------------------------------------------------------------------------
    // Copied verbatim from MyLocationDemoActivity.java
    // but commented permissionutils
    //----------------------------------------------------------------------------------------------
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            //PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
              //      Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
    //==============================================================================================


    /*@Override
    public void onConnected(Bundle connectionHint) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            }
        } catch (SecurityException noLoc) {
            //boop
        }
    }*/




    //----------------------------------------------------------------------------------------------
    // I believe these are both here because it is currently the Main screen for the application
    //----------------------------------------------------------------------------------------------

    @Override
    public void onStart() {
        super.onStart();

        //------------------------------------------------------------------------------------------
        // Added to turn on play services
        //------------------------------------------------------------------------------------------
       // mGoogleApiClient.connect();
        //------------------------------------------------------------------------------------------

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.tim_pc.projectlasso/http/host/path")
        );
        //AppIndex.AppIndexApi.start(client, viewAction);

        //------------------------------------------------------------------------------------------
        // Added to turn on play services
    }

    @Override
    public void onStop() {
        super.onStop();
        //------------------------------------------------------------------------------------------
        // Added to turn off play services
        //------------------------------------------------------------------------------------------
        //mGoogleApiClient.disconnect();
        //------------------------------------------------------------------------------------------

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.tim_pc.projectlasso/http/host/path")
        );
        //AppIndex.AppIndexApi.end(client, viewAction);
        //client.disconnect();


    }

    //==============================================================================================

}
