package com.example.fitrition.boundary;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitrition.FirstFragment;
import com.example.fitrition.MainActivity;
import com.example.fitrition.R;
import com.example.fitrition.databinding.ActivityMainBinding;
import com.example.fitrition.entities.FitnessCentre;
import com.example.fitrition.entities.FitnessCentreJSON;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executor;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class ExploreFragment extends Fragment implements OnMapReadyCallback, EasyPermissions.PermissionCallbacks, View.OnClickListener {

    MapView map;
    GoogleMap gmap;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 12;
    private static Boolean flagForInfiniteLoop = Boolean.TRUE;
    private static int counter = 0;

    private Button mapCentreLocationButton;



    //ADD
    private String URL = "https://drive.google.com/file/d/1E1XxrwbInzKtQhXl5OK16uXxeXtOqJTJ/view?usp=sharing";
    RequestQueue requestQueue;
    Gson gson;
    FitnessCentreJSON[] fitnessCentre;
    MarkerOptions marker;
    Vector<MarkerOptions> markerOptions;
    FusedLocationProviderClient client;

    private ActivityMainBinding binding;
    // Construct a PlacesClient

    //END

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        //ADD
        Log.d("test123","Testing123");
        mapCentreLocationButton = (Button) view.findViewById(R.id.mapCentreLocationButton);
        Log.d("test","Testing");

        requestPermission();

        //Used for finding location
        client = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());

        //END
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
        LatLng Garden = new LatLng(1.279689, 103.862667);
        gmap.addMarker(new MarkerOptions().position(Garden).title("Garden").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        );
        //gmap.moveCamera(CameraUpdateFactory.newLatLng(Garden));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.2816946528685949, 103.83923394187357), 13));


        //ADDED
        gson = new GsonBuilder().create();
        markerOptions = new Vector<>();
        sendRequest();

        if(markerOptions!=null && gmap!=null){
            for(int i=0;i<markerOptions.size();i++){
                gmap.addMarker(markerOptions.get(i));
            }
        }

        gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                return false;
            }
        });


        Log.d("Marker Ran before the rest", Integer.toString(markerOptions.size()));

        //CLear all markers: gmap.clear() works but the order in not linear -> that
        //is we clear first then the other markers were added
        //gmap.clear();


        //Test location
        //getDeviceLocation();
        //END



    }

    //ADD
    public void sendRequest(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,onSuccess,onError);
        requestQueue.add(stringRequest);

    }

    public Response.Listener<String> onSuccess = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            //Log.d("Sucess", "what");
            //fitnessCentre = gson.fromJson(response, FitnessCentreJSON[].class);

            try {
                //Log.d("Sucess", "C:\\Users\\Admin\\Desktop\\sampleJsonNPP.json");

                //FileReader fr =  new FileReader("C:\\Users\\Admin\\Desktop\\sampleJson.txt");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(getActivity().getApplicationContext().getAssets().open("sampleJson.txt")));
                fitnessCentre = gson.fromJson(reader, FitnessCentreJSON[].class);

                //t = gson.fromJson(new FileReader("C:\\Users\\Admin\\Desktop\\sampleJsonNPP.json"), FitnessCentreJSON[].class);
                Log.d("Sucess", "what Inner" + Integer.toString(fitnessCentre.length));
            }
            catch(Exception e) {
                Log.d("Failure", "Exception"  );
            }



            for(FitnessCentreJSON info : fitnessCentre){
                double lat = Double.parseDouble(info.latitude);
                double lng = Double.parseDouble(info.longitude);
                String title = info.name;
                //Log.d("sample", Double.toString(lat));

                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng))
                        .title(title);

                markerOptions.add(marker);
                //Log.d("Marker", Integer.toString(markerOptions.size()));

                gmap.addMarker(marker);


            }
        }
    };

    public Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getActivity().getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    //Location permission
    public void requestPermission(){

        if(hasLocationPermissions(requireContext())){
            return;}

        //Log.d("Permission", Boolean.toString(hasLocationPermissions(requireContext())));

        if(flagForInfiniteLoop.booleanValue() == Boolean.TRUE){
            //Log.d("Permission", "Q" );

            EasyPermissions.requestPermissions(this,
                    "You need to accept location Permission to display facilities near by you",
                    REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }else{
            //Log.d("Permission","WHY DID it not reach");
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //Blank
        //Log.d("Weird idk", "Don't work" );
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //Blank
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        Log.d("Permission", "On Permission Granted" );
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

    //As it is not it will zoom the onto the location
    private void getDeviceLocation() {

        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (hasLocationPermissions(requireContext())) {
                Task<Location> locationResult = client.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Log.d("getLocation","ReachBelow");

                            Location lastKnownLocation = task.getResult();
                            Log.d("Lat", String.valueOf(lastKnownLocation.getLatitude()));
                            Log.d("Lng", String.valueOf(lastKnownLocation.getLongitude()));

                            if (lastKnownLocation != null) {
                                Log.d("getLocation","ReachBelow");
                                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), 15));
                                Log.d("getLocation","Reach2");
                            }
                        } else {
                            Log.d("getLocation","Exception");
                            Log.d("TAG", "Current location is null. Using defaults.");
                            Log.e("TAG", "Exception: %s", task.getException());
                            //gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(,, 0));
                            //gmap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    @Override
    public void onClick(View view) {
        getDeviceLocation();
    }

    //END
}

//Merzen Used to track changes
//Added implementation 'com.google.code.gson:gson:2.9.0' & implementation 'com.android.volley:volley:1.2.1'
//to the build.gradle (Module : Fitrition)
//Android manifest <Application>: Android uses clear traffic = true

//Added FitnessCentreJSON entity class
//Added stuff in explorer frag ADD -> END ADD


//For location tracking
//Added Manifest background location for android Q
//easypermissions Added