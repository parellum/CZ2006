package com.example.fitrition.boundary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitrition.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ExploreFragment extends Fragment implements OnMapReadyCallback {

    MapView map;
    GoogleMap gmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        map=(MapView) view.findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        map.getMapAsync(this);
        map.onResume();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        gmap=googleMap;
        LatLng Garden = new LatLng(1.279689, 103.862667);
        gmap.addMarker(new MarkerOptions().position(Garden).title("Garden"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(Garden));

    }
}