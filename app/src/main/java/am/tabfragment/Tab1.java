package am.tabfragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Tab1 extends Fragment {

    MapView mapView;
    GoogleMap map;

    UiSettings mUiSettings;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tab1, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();



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

        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(gym, 20);
        //map.animateCamera(cameraUpdate);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(gym)      // Sets the center of the map to Mountain View
                .zoom(16)                   // Sets the zoom  //.bearing(90)   // Sets the orientation of the camera to east
                .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        return v;
    }

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

}
