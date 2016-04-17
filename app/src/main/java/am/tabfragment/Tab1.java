package am.tabfragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class Tab1 extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    MapView mapView;
    GoogleMap map;
    UiSettings mUiSettings;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 60000;  /* 60 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tab1, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        if (map != null) {
            Tab1PermissionsDispatcher.getMyLocationWithCheck(this);
        }

        mUiSettings = map.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);

        map.setBuildingsEnabled(true);

        //==========================================================================================
        // This is all preset sample code for the Map.
        // It adds five markers at Emory and moves the camera to center
        //==========================================================================================
        LatLng emoryMathCS = new LatLng(33.790235, -84.326577);
        map.addMarker(new MarkerOptions().position(emoryMathCS).title("Tim").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        map.moveCamera(CameraUpdateFactory.newLatLng(emoryMathCS));

        LatLng libs = new LatLng(33.790484, -84.322963);
        map.addMarker(new MarkerOptions().position(libs).title("Rachel").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        map.moveCamera(CameraUpdateFactory.newLatLng(libs));

        LatLng psych = new LatLng(33.790858, -84.327383);
        map.addMarker(new MarkerOptions().position(psych).title("Will").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        map.moveCamera(CameraUpdateFactory.newLatLng(psych));

        LatLng duc = new LatLng(33.793595, -84.323724);
        map.addMarker(new MarkerOptions().position(duc).title("Amanda").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        map.moveCamera(CameraUpdateFactory.newLatLng(duc));

        LatLng gym = new LatLng(33.793427, -84.325682);
        map.addMarker(new MarkerOptions().position(gym).title("Danny").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        map.moveCamera(CameraUpdateFactory.newLatLng(gym));
        //==========================================================================================


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(gym)      // Sets the center of the map to Mountain View
                .zoom(16)                   // Sets the zoom  //.bearing(90)   // Sets the orientation of the camera to east
                .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        return v;
    }

    //==============================================================================================
    //protected void loadMap(GoogleMap googleMap) {
    //    map = googleMap;
    //    if (map != null) {
    //        // Map is ready
    //        Toast.makeText(getActivity(), "Map Fragment was loaded properly!", Toast.LENGTH_SHORT).show();
    //        Tab1PermissionsDispatcher.getMyLocationWithCheck(this);
    //    } else {
    //        Toast.makeText(getActivity(), "Error - Map was null!!", Toast.LENGTH_SHORT).show();
    //    }
    //}

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Tab1PermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    //===not map====================================================================================

    @Override
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION /*, Manifest.permission.ACCESS_COARSE_LOCATION*/})
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        //int hasCourseLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (/*hasCourseLocationPermission != PackageManager.PERMISSION_GRANTED && */ hasFineLocationPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{/*Manifest.permission.ACCESS_COARSE_LOCATION,*/ Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            Toast.makeText(getActivity(), "GPS location was found!", Toast.LENGTH_SHORT).show();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)      // Sets the center of the map to Mountain View
                    .zoom(16)                   // Sets the zoom  //.bearing(90)   // Sets the orientation of the camera to east
                    .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        } else {
            Toast.makeText(getActivity(), "Current location was null, enable GPS on emulator!", Toast.LENGTH_SHORT).show();
        }
        startLocationUpdates();
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION /*, Manifest.permission.ACCESS_COARSE_LOCATION*/})
    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        //int hasCourseLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (/*hasCourseLocationPermission != PackageManager.PERMISSION_GRANTED && */ hasFineLocationPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{/*Manifest.permission.ACCESS_COARSE_LOCATION,*/ Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                 mLocationRequest, this);
    }

    public void onLocationChanged(Location location) {
        // Report to the UI that the location was updated
        //String msg = "Updated Location: " +
                //Double.toString(location.getLatitude()) + "," +
                //Double.toString(location.getLongitude());
        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        //CameraPosition cameraPosition = new CameraPosition.Builder()
        //        .target(latLng)      // Sets the center of the map to Mountain View
        //        .zoom(18)                   // Sets the zoom  //.bearing(90)   // Sets the orientation of the camera to east
        //        .tilt(45)                   // Sets the tilt of the camera to 30 degrees
        //        .build();                   // Creates a CameraPosition from the builder
        //map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(getActivity(), "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(getActivity(), "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(),
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Sorry. Location services not available to you", Toast.LENGTH_LONG).show();
        }
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void getMyLocation() {
        if (map != null) {
            // Now that map has loaded, let's get our location!
            //int hasCourseLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
            if (/*hasCourseLocationPermission != PackageManager.PERMISSION_GRANTED && */ hasFineLocationPermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(), new String[]{/*Manifest.permission.ACCESS_COARSE_LOCATION,*/ Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            map.setMyLocationEnabled(true);
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            connectClient();
        }
    }

    protected void connectClient() {
        // Connect the client.
        if (isGooglePlayServicesAvailable() && mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        } else {
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(getActivity().getSupportFragmentManager(), "Location Updates");
            }

            return false;
        }
    }

    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }


    //==============================================================================================
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    class TYMySQLHandler extends AsyncTask<String, String , String> {


        String resultString = "";
        JSONObject result = null;
        JSONArray resultArray = null;

        InputStream is = null;
        OkHttpClient client = new OkHttpClient();

        public TYMySQLHandler(){

        }
        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... params) {


            if(params[0].equals("s")) {

                try {
                    resultString = run("http://ec2-52-87-164-152.compute-1.amazonaws.com/grabGroupMembers.php?username=" + params[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    System.out.println(resultString);
                    result = new JSONObject(resultString);


                    resultArray = result.getJSONArray("users");
                    if (result.getInt("success") == 1) {


                        for (int i = 0; i < resultArray.length(); i++) {
                            JSONObject temp = resultArray.getJSONObject(i);
                            TYUser user = new TYUser(temp.getString("name"), R.mipmap.face1, temp.getString("email"), temp.getString("phonenumber"), temp.getString("username"), temp.getInt("groupID"));

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if(params[0].equals("a")){

            }

            return "noparams";
        }


        @Override
        protected void onPostExecute(String string){

        }



    }

}
