package com.example.mypet3;

import static com.example.mypet3.LoginActivity.loggedUser;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import DBClass.Park;

public class ParkTabFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference dbRef;
    ParkAdapter myAdapter;
    ArrayList<Park> ParkList;

    ParkTabFragment(){}


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton buttAddPark = (FloatingActionButton)view.findViewById(R.id.btnAddPark);

        if(loggedUser!=""){
            buttAddPark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Da HomeFragment a AddParkActivity
                    startActivity( new Intent(getActivity().getApplicationContext(), ParkAddActivity.class));
                }
            });
        }
        else{
            buttAddPark.setVisibility(View.GONE);
        }

        recyclerView = view.findViewById(R.id.parkRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ParkList = new ArrayList<>();
        myAdapter = new ParkAdapter(ParkList,getActivity());
        recyclerView.setAdapter(myAdapter);



        if (ParkList != null) {
            ParkList.clear();
        }
/*
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myPark---android-app-default-rtdb.firebaseio.com/");

        dbRef.child("Park").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn : snapshot.getChildren()){
                    Park p = sn.getValue(Park.class);
                    ParkList.add(p);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parks, container, false);

        setHasOptionsMenu(true);
        return view;
    }


}