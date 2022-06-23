package com.example.mypet3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import DBClass.Pet;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference database;
    DatabaseReference dbRef;
    HomeAdapter myAdapter;
    ArrayList<Pet> list;

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //da riempire
        ArrayList<Pet> listaPet = new ArrayList<>();

        Query getPetData = FirebaseDatabase.getInstance().getReference("Pet");
        Query getPetName = getPetData.orderByChild("Nome");

        getPetName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn : snapshot.getChildren()){
                    Pet p = sn.getValue(Pet.class);
                    //aggiungi a lista?
                    listaPet.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
    }*/

    /*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.petlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        myAdapter = new HomeAdapter(getContext(), list);
        recyclerView.setAdapter(myAdapter);

        database = FirebaseDatabase.getInstance().getReference("Pet");
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mypet---android-app-default-rtdb.firebaseio.com/");

        // database.addValueEventListener(new ValueEventListener() {
        dbRef.child("Museum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn : snapshot.getChildren()){
                    Pet p = sn.getValue(Pet.class);
                    list.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Cambia da fragment ad activity
        FloatingActionButton buttAdd = (FloatingActionButton)view.findViewById(R.id.btnAdd);
        buttAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(getActivity().getApplicationContext(), AddPetActivity.class));
            }
        });

        //TextView PetNameD =  view.findViewById(R.id.txtDescr1);
        //PetNameD.setText("aa");

        recyclerView = view.findViewById(R.id.petlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        myAdapter = new HomeAdapter(getContext(), list);
        recyclerView.setAdapter(myAdapter);

        database = FirebaseDatabase.getInstance().getReference("Pet");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn : snapshot.getChildren()){
                    Pet p = sn.getValue(Pet.class);
                    list.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }


}