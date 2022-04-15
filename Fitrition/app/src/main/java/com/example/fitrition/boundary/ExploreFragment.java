package com.example.fitrition.boundary;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitrition.R;
import com.example.fitrition.control.FacilityManager;
//import com.example.fitrition.databinding.ActivityMainBinding;
import com.example.fitrition.entities.Fitness;
import com.example.fitrition.entities.HawkerCentre;
import com.example.fitrition.entities.ViewFacilitiesActivity;
import com.example.fitrition.entities.ViewFacilitiesActivityFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class ExploreFragment extends Fragment implements OnMapReadyCallback, EasyPermissions.PermissionCallbacks, View.OnClickListener {

    MapView map;
    GoogleMap gmap;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 12;
    private static Boolean flagForInfiniteLoop = Boolean.TRUE;
    private static int counter = 0;

    private Button mapCentreLocationButton;

    private Marker oldHawkerMarker = null;
    private Marker oldFitnessMarker = null;

    FacilityManager facilityManager;

    Vector<MarkerOptions> markerOptionsVectorForHawkerCentre;
    Vector<MarkerOptions> markerOptionsVectorForFitnessCentre;
    FusedLocationProviderClient client;

    //Adding of database stuff - JAC
    private DatabaseReference mDataRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        facilityManager=FacilityManager.getInstance();

        mapCentreLocationButton = (Button) view.findViewById(R.id.mapCentreLocationButton);

        //Request location access from user
        requestPermission();

        //Used for finding location
        client = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());

        map=(MapView) view.findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        map.getMapAsync(this);
        map.onResume();

        mapCentreLocationButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        gmap=googleMap;

        //Set default location as Chinese Garden
        //LatLng Garden = new LatLng(1.279689, 103.862667);
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.279689, 103.862667), 13));

        markerOptionsVectorForHawkerCentre = new Vector<>();
        markerOptionsVectorForFitnessCentre = new Vector<>();

        //Populate the map with all markers whenever it first load
        createAllMapMarkers();

        //Code to handle the popping up of the new fragment when a marker is pressed
        gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                //Handles the case where the user presses on the blue icon by accident
                if(marker.getTitle().equalsIgnoreCase("You Are Here"))
                    return false;


                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                //Attempt to launch a new activity cuz this fragment is literally flooded
                ViewFacilitiesActivity t = ViewFacilitiesActivityFactory.getFacility((String) marker.getTag());

                Intent intent = new Intent(getContext(), t.getClass());
                Bundle b = new Bundle();

                b.putString("facilitiesName", marker.getTitle());
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);

                if(marker.getTag().toString().equalsIgnoreCase("Fitness")){
                    if(oldFitnessMarker != null && !(oldFitnessMarker.getTitle().equalsIgnoreCase(marker.getTitle()))){//oldFitnessMarker != marker
                        oldFitnessMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                        if(oldHawkerMarker != null)
                            oldHawkerMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }
                    oldFitnessMarker = marker;
                }

                if(marker.getTag().toString().equalsIgnoreCase("Hawker")){
                    if(oldHawkerMarker != null && !(oldHawkerMarker.getTitle().equalsIgnoreCase(marker.getTitle()))){//oldHawkerMarker != marker
                        oldHawkerMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        if(oldFitnessMarker != null)
                            oldFitnessMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                    }
                    oldHawkerMarker = marker;
                }
                return false;
            }
        });
    }


    //==============================Used to Data base request======================================

    public void createAllMapMarkers(){

        //Add hawker marker
        ArrayList <HawkerCentre> hawkerList = new ViewHawkerActivity().getAllHawkerArrayList();

        for(HawkerCentre info : hawkerList){
            double lat = Double.parseDouble(info.getLatitude());
            double lng = Double.parseDouble(info.getLongitude());
            String title = info.getName();



                //HAWKER
                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng))
                        .title(title);

                markerOptionsVectorForHawkerCentre.add(marker);
                gmap.addMarker(marker).setTag("Hawker");

        }
        //Add Fitness marker
        ArrayList <Fitness> fitnessList = new ViewFitnessActivity().getAllFitnessArrayList();
        for(Fitness info : fitnessList){
            double lat = Double.parseDouble(info.getLatitude());
            double lng = Double.parseDouble(info.getLongitude());
            String title = info.getName();

            //Fitness
            MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng))
                    .title(title).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            markerOptionsVectorForFitnessCentre.add(marker);
            gmap.addMarker(marker).setTag("Fitness");
        }

        }

    //=================Used to handle Location permission ===================================
    public void requestPermission(){

        if(hasLocationPermissions(requireContext())){
            return;}

        if(flagForInfiniteLoop.booleanValue() == Boolean.TRUE){
            EasyPermissions.requestPermissions(this,
                    "You need to accept location Permission to display facilities near by you",
                    REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }else{
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //Blank

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //Blank
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        counter += 1;
        if(counter >= 2){
            flagForInfiniteLoop = Boolean.FALSE;

        }
    }

    public boolean hasLocationPermissions(Context context){

        return EasyPermissions.hasPermissions(context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        );
    }
    //End of location permission handling

    //Used to centre camera location and filter those within 2 km
    //Flow is getCurrent location -> FacilitiesManger (to compute those within 2 km)-> add marker
    private void getDeviceLocationInLocation() {

        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        AtomicReference<Double> lat = new AtomicReference<>((double) 0);
        AtomicReference<Double> lng = new AtomicReference<>((double) 0);

        try {
            if (hasLocationPermissions(requireContext())) {

                client.getCurrentLocation(100, new CancellationToken() {
                    @NonNull
                    @Override
                    public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                        return null;
                    }

                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }

                }).addOnSuccessListener(location -> {
                    Toast.makeText(getActivity(),
                            "Successfully centred location",
                            Toast.LENGTH_LONG)
                            .show();

                    Location locationResult = location;
                    lat.set(locationResult.getLatitude());
                    lng.set(locationResult.getLongitude());

                    createAllMapMarkers();
                    FacilityManager.getInstance().obtainFacilityBasedOnDistance(markerOptionsVectorForHawkerCentre,lat.get(), lng.get());
                    FacilityManager.getInstance().obtainFacilityBasedOnDistance(markerOptionsVectorForFitnessCentre,lat.get(), lng.get());

                    gmap.clear();
                    for (int i = 0; i < markerOptionsVectorForHawkerCentre.size(); i++) {

                        gmap.addMarker(markerOptionsVectorForHawkerCentre.get(i)).setTag("Hawker");

                    }

                    for (int i = 0; i < markerOptionsVectorForFitnessCentre.size(); i++) {

                        gmap.addMarker(markerOptionsVectorForFitnessCentre.get(i)).setTag("Fitness");
                    }

                    gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(locationResult.getLatitude(),
                                    locationResult.getLongitude()), 14));

                    //Add a blue marker to indicate user location
                    MarkerOptions curLocMarker = new MarkerOptions().position(new LatLng(lat.get(), lng.get()))
                            .title("You Are Here")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                    gmap.addMarker(curLocMarker);
                });

            }else{
                Toast.makeText(getActivity(),
                        "Unsuccessful please enable location and try again",
                        Toast.LENGTH_LONG)
                        .show();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }

    }

    @Override
    public void onClick(View view) {
        //Warn user of the delay
        Toast.makeText(getActivity(),
                "Please wait UP TO 7 seconds",
                Toast.LENGTH_LONG)
                .show();


        //Used to centre camera to current location AND filter markers to show only those
        //within 2 km
        getDeviceLocationInLocation();

    }

}
