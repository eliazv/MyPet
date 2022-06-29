package com.example.mypet3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.Utilities;
//import com.google.protobuf.DescriptorProtos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import DBClass.Pet;

public class MapFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    SearchView sv_map;
    Pet markerPet = null;
    ArrayList<MarkerOptions> mMarkerArray = new ArrayList<MarkerOptions>();

    Pet currentPet;
    String addrCurrent;

    DatabaseReference dbRef;

    List<MarkerOptions> markers;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public MapFragment(Pet currentPet) {
        this.currentPet = currentPet;
    }

    public MapFragment(String addr) {
        this.addrCurrent = addr;
    }

    public MapFragment() { }
    /*
    @RequiresApi(api = Build.VERSION_CODES.N)
    public MapFragment(String nomeP, String user) {
        zoomPetMarker(nomeP,user);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        sv_map = view.findViewById(R.id.sv_map);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        markers = new Vector<>();

        markerPet = null;

        sv_map.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = sv_map.getQuery().toString();
                List<Address> addressList = null;
                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        mapFragment.getMapAsync(this);
        return view;
    }

    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            return;
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);

        googleMap.setPadding(0, 150, 0, 0); //numTop = padding of your choice

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mypet---android-app-default-rtdb.firebaseio.com/");

        mMap.setOnMarkerClickListener(this);

        setMarker();

/*
        if(currentPet != null){
            LatLng posCurrent=getLocationFromAddress(currentPet.getIndirizzo());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(posCurrent.latitude, posCurrent.longitude),15));
        }*/
        if(addrCurrent!=null){
            LatLng posCurrent=getLocationFromAddress(addrCurrent);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(posCurrent.latitude, posCurrent.longitude),16));

        }
        else{
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.4254152,11.723322),6));
        }
    }

    private void setMarker() {
        if (markers != null) {
            markers.clear();
        }
        dbRef.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Pet Pet = dataSnapshot.getValue(Pet.class);
                    //mette il marker
                    LatLng ll =  getLocationFromAddress(Pet.getIndirizzo());
                    MarkerOptions m = new MarkerOptions().title(Pet.getNome()+"-"+Pet.getProprietario()).position(new LatLng(ll.latitude, ll.longitude))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    mMap.addMarker(m);
                    mMarkerArray.add(m);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Sei qui!");
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        /*
        Pet markerPet = getMarketPet(marker.getTitle());

        if(markerPet != null){
            Utilities.insertFragment((AppCompatActivity) getActivity(), new PetFragment(markerPet), PetFragment.class.getSimpleName());
        }*/

        //TODO da cambiare con il pet giusto
        FragmentManager fm = getFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            String nomeM = marker.getTitle();
            //spezza in due dove c'è -
            String[] petData = nomeM.split("-");
            ft.replace(R.id.frame_layout, new PetFragment(petData[0],petData[1]));
            ft.commit();
        }
        return false;
    }

    private Pet getMarketPet(String PetName) {
        dbRef.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String getPet = dataSnapshot.child("nome").getValue(String.class);
                    if (getPet.equals(PetName)) {
                        markerPet = dataSnapshot.getValue(Pet.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return markerPet;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void zoomPetMarker(String nomeP, String User){
        //trovare il marker con il titolo giusto
        MarkerOptions pm = mMarkerArray.stream().filter(m -> m.getTitle().equals(nomeP+"-"+User)).findFirst().orElse(null);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pm.getPosition().latitude, pm.getPosition().longitude),9));

    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getContext());
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng((double) (location.getLatitude() ),
                    (double) (location.getLongitude()));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}